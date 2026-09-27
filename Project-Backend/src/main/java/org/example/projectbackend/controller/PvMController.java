package org.example.projectbackend.controller;

import org.example.projectbackend.entity.FightResult;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.UserRepository;
import org.example.projectbackend.service.FightService;
import org.example.projectbackend.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expedition")
@CrossOrigin(origins = "http://localhost:4200")
public class PvMController {
    private final GameService gameService;
    UserRepository userRepository;
    FightService fightService;
    public PvMController(UserRepository userRepository, FightService fightService, GameService gameService) {
        this.userRepository = userRepository;
        this.fightService = fightService;
        this.gameService = gameService;
    }

    @GetMapping("/{monsterName}")
    public ResponseEntity<?> startExpedition(@PathVariable String monsterName,
                                          @AuthenticationPrincipal String username) {
        User user =  userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        if(player.getQuestPoints()>0) {
            player.setQuestPoints(player.getQuestPoints()-1);
            System.out.println("Monster name: " + monsterName + "\nusername: " + username);
            FightResult fightResult = fightService.createPvM(username, monsterName);
            System.out.println("fightResult: " + fightResult.toString());
            gameService.lvlUp(username);
            return ResponseEntity.ok(fightResult);
        }else {
            return ResponseEntity.badRequest().body("No quest points!");
        }
    }

}
