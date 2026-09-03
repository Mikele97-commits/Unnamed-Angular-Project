package org.example.projectbackend.repository;

import org.example.projectbackend.entity.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonsterRepository extends JpaRepository<Monster, Integer> {

    Monster getMonsterByName(String name);
}
