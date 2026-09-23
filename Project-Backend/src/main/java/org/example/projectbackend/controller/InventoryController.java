package org.example.projectbackend.controller;

import org.example.projectbackend.entity.Inventory;
import org.example.projectbackend.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:4200")
public class InventoryController {
    InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("get")
    public List<Inventory> getInventory(@AuthenticationPrincipal String username) {
        return inventoryService.getInventory(username);
    }

    @PostMapping("equipItem")
    public ResponseEntity<String> equipItem(@AuthenticationPrincipal String username, @RequestBody int itemId){
        inventoryService.equipItem(username,itemId);
        return ResponseEntity.ok().build();
    }

}

