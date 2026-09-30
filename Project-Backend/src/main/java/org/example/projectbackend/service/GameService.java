package org.example.projectbackend.service;

import org.example.projectbackend.dto.TopDivDto;
import org.example.projectbackend.entity.Inventory;
import org.example.projectbackend.entity.Monster;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.entity.items.ItemTemplate;
import org.example.projectbackend.repository.InventoryRepository;
import org.example.projectbackend.repository.ItemTemplateRepository;
import org.example.projectbackend.repository.MonsterRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class GameService {
    UserRepository userRepository;
    MonsterRepository monsterRepository;
    ItemTemplateRepository itemTemplateRepository;
    PlayerService playerService;

    public GameService(PlayerService playerService, ItemTemplateRepository itemTemplateRepository, MonsterRepository monsterRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.monsterRepository = monsterRepository;
        this.itemTemplateRepository = itemTemplateRepository;
        this.playerService = playerService;
    }

    public TopDivDto giveTopDto(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        System.out.println("Taking data of player "+user.getUsername());
        int[] damages=playerService.calculateDmg(player);
        int armor=armor(player);
        int[] regenTime = calculateTimeToRegen();
        System.out.println(Arrays.toString(regenTime));
        return new TopDivDto(damages[0],damages[1], armor, username, player.getLvl(), player.getCurrEnergy(), player.getMaxEnergy(), player.getCurrentHP(), player.getFinalHP(), player.getCurrentExp(), player.getNxtLvlExp(), player.getQuestPoints(), player.getGold(), regenTime);
    }

    public int[] calculateMonsterDmg(String monsterName){
        Monster monster = monsterRepository.getMonsterByName(monsterName);
       int minDmg=monster.getMinDamage() + monster.getStrength()/5;
       int maxDmg=monster.getMaxDamage() + monster.getStrength()/5;
       return new int[]{minDmg,maxDmg};
    }

    public int[] calculateTimeToRegen(){
        LocalDateTime now = LocalDateTime.now();

        int minute = now.getMinute();
        int second = now.getSecond();

        int nextTickMinute;
        if (minute < 15) nextTickMinute = 15;
        else if (minute < 30) nextTickMinute = 30;
        else if (minute < 45) nextTickMinute = 45;
        else nextTickMinute = 60;

        int minutesLeft = nextTickMinute - minute - 1;
        int secondsLeft = 59 - second;

        return new int[]{minutesLeft,secondsLeft};
    }
    public int armor(Player player){
        int baseArmor=5*player.getLvl();
        if(player.getEquippedArmor()==null){
            return baseArmor;
        }
        return baseArmor+player.getEquippedArmor().getFinalArmor();
    }



    public boolean hasLoot() {
        int rand = new Random().nextInt(100);
        return rand < 70;
    }

    public int randomizeLoot(Monster monster){
        int monsterLvl=monster.getLevel();
        int minLvl=monsterLvl-3;
        if(minLvl<1){
            minLvl=1;
        }
        int maxLvl=monsterLvl+3;
        List<ItemTemplate> itemTemplates=itemTemplateRepository.findByLvlBetween(minLvl, maxLvl);
        int rnd = new Random().nextInt(itemTemplates.size());
        return Math.toIntExact(itemTemplates.get(rnd).getId());
    }
}
