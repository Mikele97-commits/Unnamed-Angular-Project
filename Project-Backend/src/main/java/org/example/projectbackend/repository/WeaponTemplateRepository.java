package org.example.projectbackend.repository;

import org.example.projectbackend.entity.items.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponTemplateRepository extends JpaRepository<Weapon, String> {
}
