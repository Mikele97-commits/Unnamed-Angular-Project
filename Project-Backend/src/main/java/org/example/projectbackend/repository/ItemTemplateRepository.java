package org.example.projectbackend.repository;

import org.example.projectbackend.entity.items.ItemTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTemplateRepository extends JpaRepository<ItemTemplate, Integer> {
}

