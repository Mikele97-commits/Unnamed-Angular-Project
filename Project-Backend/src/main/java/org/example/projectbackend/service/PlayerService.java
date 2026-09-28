package org.example.projectbackend.service;

import jakarta.transaction.Transactional;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.repository.PlayerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

        PlayerRepository playerRepository;
        public PlayerService(PlayerRepository playerRepository) {
            this.playerRepository = playerRepository;
        }

        @Scheduled(cron = "0 0,15,30,45 * * * *")
        @Transactional
        public void regenerateEnergy() {
            //HP regeneration
            List<Player> injuredPlayers = playerRepository.findInjuredPlayers();
            for (Player p : injuredPlayers) {
                p.setCurrentHP(Math.min(p.getFinalHP(), p.getCurrentHP() + (int)(p.getFinalHP() *0.1)));
            }
            playerRepository.saveAll(injuredPlayers);


            //Energy regeneration
            List<Player> noFullEnergyPlayers= playerRepository.findNoFullEnergyPlayers();
            for (Player p : noFullEnergyPlayers) {
                p.setCurrEnergy(p.getCurrEnergy() + 5);
            }
            playerRepository.saveAll(noFullEnergyPlayers);
        }
}
