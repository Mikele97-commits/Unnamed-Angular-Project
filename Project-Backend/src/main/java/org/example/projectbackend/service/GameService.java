package org.example.projectbackend.service;

import org.example.projectbackend.dto.TopDivDto;
import org.example.projectbackend.entity.Monster;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.MonsterRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    UserRepository userRepository;
    MonsterRepository monsterRepository;

    public GameService(MonsterRepository monsterRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.monsterRepository = monsterRepository;
    }

    public TopDivDto giveTopDto(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        System.out.println("Taking data of player "+user.getUsername());
        int[] damages=calculateDmg(username);
        int armor=armor(username);
        return new TopDivDto(damages[0],damages[1], armor, username, player.getCurrEnergy(), player.getMaxEnergy(), player.getCurrentHP(), player.getFinalHP(), player.getCurrentExp(), player.getNxtLvlExp(), player.getQuestPoints(), player.getGold());
    }

    public int[] calculateMonsterDmg(String monsterName){
        Monster monster = monsterRepository.getMonsterByName(monsterName);
       int minDmg=monster.getMinDamage() + monster.getStrength()/5;
       int maxDmg=monster.getMaxDamage() + monster.getStrength()/5;
       return new int[]{minDmg,maxDmg};
    }
    public int[] calculateDmg(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
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

    public int armor(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        int baseArmor=5*player.getLvl();
        if(player.getEquippedArmor()==null){
            return baseArmor;
        }
        return baseArmor+player.getEquippedArmor().getFinalArmor();
    }
}
