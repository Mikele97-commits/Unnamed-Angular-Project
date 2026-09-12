package org.example.projectbackend.controller;

import org.example.projectbackend.repository.InventoryRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:4200")
public class InventoryController {
    InventoryRepository inventoryRepository;
    UserRepository userRepository;

    public InventoryController(InventoryRepository inventoryRepository, UserRepository userRepository) {
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }

}
