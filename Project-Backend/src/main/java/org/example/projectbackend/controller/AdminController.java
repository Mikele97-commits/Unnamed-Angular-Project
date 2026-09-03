package org.example.projectbackend.controller;

import jakarta.transaction.Transactional;
import org.example.projectbackend.dto.AdminDto;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PostMapping("/set-energy")
    @Transactional
    public ResponseEntity<String> setEnergy(@RequestBody AdminDto adminDto) {
        User user = userRepository.findByUsername(adminDto.username()).orElse(null);
        Player player = user.getPlayer();
        player.setCurrEnergy(adminDto.amount());
        if (player.getCurrEnergy()>player.getMaxEnergy()) {
            player.setCurrEnergy(player.getMaxEnergy());
        }
        if(player.getCurrEnergy()<0) {
            player.setCurrEnergy(0);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/set-hp")
    @Transactional
    public ResponseEntity<String> setHp(@RequestBody AdminDto adminDto) {
        User user = userRepository.findByUsername(adminDto.username()).orElse(null);
        Player player = user.getPlayer();
        player.setCurrentHP(adminDto.amount());
        if(player.getCurrentHP()>player.getFinalHP()){
            player.setCurrentHP(player.getFinalHP());
        }
        if(player.getCurrentHP()<0){
            player.setCurrentHP(0);
        }
        return ResponseEntity.ok().build();
    }

}
