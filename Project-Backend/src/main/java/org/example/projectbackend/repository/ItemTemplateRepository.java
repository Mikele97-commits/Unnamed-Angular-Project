package org.example.projectbackend.repository;

import org.example.projectbackend.entity.items.ItemTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemTemplateRepository extends JpaRepository<ItemTemplate, Integer> {
    List<ItemTemplate> findByLvlBetween(int minLvl, int maxLvl);
}

