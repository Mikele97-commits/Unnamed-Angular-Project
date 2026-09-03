package org.example.projectbackend.controller;

import org.example.projectbackend.entity.FightResult;
import org.example.projectbackend.repository.UserRepository;
import org.example.projectbackend.service.FightService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expedition")
@CrossOrigin(origins = "http://localhost:4200")
public class PvMController {
    UserRepository userRepository;
    FightService fightService;
    public PvMController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{monsterName}")
    public FightResult startExpedition(@PathVariable String monsterName,
                                       @AuthenticationPrincipal String username) {
        return fightService.createPvM(username, monsterName);
    }

}
