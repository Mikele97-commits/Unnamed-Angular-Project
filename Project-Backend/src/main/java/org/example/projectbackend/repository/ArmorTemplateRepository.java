package org.example.projectbackend.repository;

import org.example.projectbackend.entity.items.Armor;
import org.example.projectbackend.entity.items.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorTemplateRepository extends JpaRepository<Armor, String> {
}
