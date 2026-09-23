package org.example.projectbackend.repository;

import org.example.projectbackend.entity.Inventory;
import org.example.projectbackend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
    Optional<Inventory> findByPlayerAndTypeAndEquippedTrue(Player player, String type);
}
