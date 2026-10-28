package org.example.projectbackend.controller;

import jakarta.transaction.Transactional;
import org.example.projectbackend.dto.AdminDto;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.ItemTemplateRepository;
import org.example.projectbackend.repository.UserRepository;
import org.example.projectbackend.service.InventoryService;
import org.example.projectbackend.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    UserRepository userRepository;
    ItemTemplateRepository itemTemplateRepository;
    InventoryService inventoryService;
    PlayerService playerService;

    public AdminController(PlayerService playerService, InventoryService inventoryService, UserRepository userRepository,ItemTemplateRepository itemTemplateRepository) {
        this.userRepository = userRepository;
        this.itemTemplateRepository = itemTemplateRepository;
        this.inventoryService = inventoryService;
        this.playerService = playerService;
    }
    @PostMapping("/set-energy")
    @Transactional
    public ResponseEntity<String> setEnergy(@RequestBody AdminDto adminDto) {
        Player player = playerService.getPlayer(adminDto.username());
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
        Player player = playerService.getPlayer(adminDto.username());
        player.setCurrentHP(adminDto.amount());
        if(player.getCurrentHP()>player.getFinalHP()){
            player.setCurrentHP(player.getFinalHP());
        }
        if(player.getCurrentHP()<0){
            player.setCurrentHP(0);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-item")
    @Transactional
    public ResponseEntity<String> addItem(@RequestBody AdminDto adminDto) {
        inventoryService.addItem(adminDto.username(), adminDto.amount());
        return ResponseEntity.ok().build();
    }



}
