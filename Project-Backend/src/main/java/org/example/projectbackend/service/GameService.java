package org.example.projectbackend.service;

import org.example.projectbackend.dto.TopDivDto;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    UserRepository userRepository;

    public GameService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TopDivDto giveTopDto(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        System.out.println("Taking data of player "+user.getUsername());
        int[] damages=calculateDmg(username);
        return new TopDivDto(damages[0],damages[1], username, player.getCurrEnergy(), player.getMaxEnergy(), player.getCurrentHP(), player.getFinalHP(), player.getCurrentExp(), player.getNxtLvlExp(), player.getQuestPoints(), player.getGold());
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
}
