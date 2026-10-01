package org.example.projectbackend.controller;

import org.example.projectbackend.dto.ProfileDto;
import org.example.projectbackend.entity.FightResult;
import org.example.projectbackend.service.ArenaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arena")
@CrossOrigin(origins = "http://localhost:4200")
public class ArenaController {
    ArenaService arenaService;
    public ArenaController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @GetMapping("/find/{lvl}")
    public List<ProfileDto> findEnemies(@PathVariable("lvl") int lvl){
        return arenaService.findEnemies(lvl);
    }

    @PostMapping("/attack")
    public FightResult attack(@AuthenticationPrincipal String attacker, @RequestBody String defender){
        return arenaService.initiateFight(attacker, defender);
    }
}
