package org.example.projectbackend.controller;

import org.example.projectbackend.entity.FightResult;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.service.FightService;
import org.example.projectbackend.service.PlayerService;
import org.example.projectbackend.service.PvMService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expedition")
@CrossOrigin(origins = "http://localhost:4200")
public class PvMController {
    FightService fightService;
    PlayerService playerService;
    PvMService  pvMService;
    public PvMController(PvMService pvMService, PlayerService playerService,  FightService fightService) {
        this.fightService = fightService;
        this.playerService = playerService;
        this.pvMService = pvMService;
    }

    @GetMapping("/{monsterName}")
    public ResponseEntity<?> startExpedition(@PathVariable String monsterName,
                                          @AuthenticationPrincipal String username) {
        Player player = pvMService.isValidExpedition(username);

        if(player!=null) {
            FightResult fightResult = pvMService.createPvM(username, monsterName);
            playerService.lvlUp(player);
            return ResponseEntity.ok(fightResult);
        }else {
            return ResponseEntity.badRequest().body("No quest points or HP below 20!");
        }
    }

}
