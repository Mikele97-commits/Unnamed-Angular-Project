package org.example.projectbackend.repository;

import org.example.projectbackend.dto.ProfileDto;
import org.example.projectbackend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query("SELECT p FROM Player p WHERE p.currentHP < p.finalHP")
    List<Player> findInjuredPlayers();

    @Query("SELECT p FROM Player p WHERE p.currEnergy<p.maxEnergy")
    List<Player> findNoFullEnergyPlayers();

    List<Player> findAllByQuestPointsLessThan(int i);

    List<Player> findAllByLvlBetween(int lvlAfter, int lvlBefore);
}