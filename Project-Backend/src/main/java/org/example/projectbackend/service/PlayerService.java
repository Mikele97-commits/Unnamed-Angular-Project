package org.example.projectbackend.service;

import jakarta.transaction.Transactional;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.PlayerRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

        PlayerRepository playerRepository;
        UserRepository userRepository;
        public PlayerService(PlayerRepository playerRepository, UserRepository userRepository) {
            this.playerRepository = playerRepository;
            this.userRepository = userRepository;
        }

        @Scheduled(cron = "0 0,15,30,45 * * * *")
        @Transactional
        public void regenerate() {
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

        List<Player> noFullQPPlayers= playerRepository.findAllByQuestPointsLessThan(12);
        for (Player p : noFullQPPlayers) {
            p.setQuestPoints(p.getQuestPoints()+1);
        }
        playerRepository.saveAll(noFullQPPlayers);
        }

        public void maxHPRecalculation(Player player) {
            player.setFinalHP(player.getBaseHP()+player.getEndurance()*5 + player.getLvl()*20);
        }

    public boolean lvlUp(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        if(player.getCurrentExp()>=player.getNxtLvlExp()){
            player.setLvl(player.getLvl()+1);
            player.setCurrentExp(0);
            player.setNxtLvlExp((int) (100 * player.getLvl() * Math.pow(1.1, player.getLvl()) - 1));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public int[] calculateDmg(Player player){
        int min;
        int max;
        if(player.getEquippedWeapon()!=null){
            min=player.getEquippedWeapon().getFinalMinDmg();
            max=player.getEquippedWeapon().getFinalMaxDmg();
        }else{
            min=0;
            max=0;
        }
        int finalMin=player.getBaseDmg()+min+ player.getStrength()/5;
        int finalMax=player.getBaseDmg()+max+ player.getStrength()/5;
        return new int[]{finalMin,finalMax};
    }

}
