package org.example.projectbackend.repository;

import org.example.projectbackend.entity.FightResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PvMRepository extends JpaRepository<FightResult,Integer> {
}
