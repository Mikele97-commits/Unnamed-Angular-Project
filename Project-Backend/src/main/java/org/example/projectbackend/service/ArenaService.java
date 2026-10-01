package org.example.projectbackend.service;

import org.example.projectbackend.dto.ProfileDto;
import org.example.projectbackend.entity.FightResult;
import org.example.projectbackend.entity.Inventory;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.InventoryRepository;
import org.example.projectbackend.repository.PlayerRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
public class ArenaService {
    private final UserRepository userRepository;
    PlayerRepository playerRepository;
    InventoryRepository inventoryRepository;
    PvPService pvpService;
    public ArenaService(PvPService pvpService, InventoryRepository inventoryRepository, PlayerRepository playerRepository, UserRepository userRepository) {
        this.playerRepository = playerRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.pvpService = pvpService;
    }

    public List<ProfileDto> findEnemies(int lvl){
        int minLvl=Math.max(1,lvl-3);
        int maxLvl=lvl+3;
        List<Player> enemies = playerRepository.findAllByLvlBetween(minLvl,maxLvl);
        List<ProfileDto> profileDtos = new ArrayList<>();
        if(enemies.size()<=4){
            for(Player p:enemies){
                String username=p.getUser().getUsername();
                String equippedArmor=null;
                if(p.getEquippedArmor()!=null){
                    equippedArmor=p.getEquippedArmor().getName() + " +" +  p.getEquippedArmor().getFinalPlus();
                }
                String equippedWeapon=null;
                if(p.getEquippedWeapon()!=null){
                    equippedWeapon=p.getEquippedWeapon().getName() + " +" + p.getEquippedWeapon().getFinalPlus();
                }
                profileDtos.add(new ProfileDto(username,p.getLvl(),equippedArmor,equippedWeapon,p.getCurrentHP(),p.getFinalHP()));
            }
            return profileDtos;
        }else{
            int size=enemies.size();
            HashSet<Integer> set = new HashSet<>();
            Random rand = new Random();
            for(int i=0;i<4;i++){
                int number=rand.nextInt(size);
                while(set.contains(number)){
                    number=rand.nextInt(size);
                }
                Player p=enemies.get(number);
                String username=p.getUser().getUsername();
                String equippedArmor=null;
                if(p.getEquippedArmor()!=null){
                    equippedArmor=p.getEquippedArmor().getName() + " +" +  p.getEquippedArmor().getFinalPlus();
                }
                String equippedWeapon=null;
                if(p.getEquippedWeapon()!=null){
                    equippedWeapon=p.getEquippedWeapon().getName() + " +" + p.getEquippedWeapon().getFinalPlus();
                }
                profileDtos.add(new ProfileDto(username,p.getLvl(),equippedArmor,equippedWeapon,p.getCurrentHP(),p.getFinalHP()));
                set.add(number);
            }
        }
        return profileDtos;
    }

    public FightResult initiateFight(String attacker, String defender){
        return pvpService.createPVP(attacker,defender);
    }
}
