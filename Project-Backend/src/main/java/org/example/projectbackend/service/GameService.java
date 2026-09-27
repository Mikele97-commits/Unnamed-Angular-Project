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

import java.util.List;
import java.util.Random;

@Service
public class GameService {
    UserRepository userRepository;
    MonsterRepository monsterRepository;
    ItemTemplateRepository itemTemplateRepository;
    public GameService(ItemTemplateRepository itemTemplateRepository, MonsterRepository monsterRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.monsterRepository = monsterRepository;
        this.itemTemplateRepository = itemTemplateRepository;
    }

    public TopDivDto giveTopDto(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        System.out.println("Taking data of player "+user.getUsername());
        int[] damages=calculateDmg(username);
        int armor=armor(username);
        return new TopDivDto(damages[0],damages[1], armor, username, player.getLvl(), player.getCurrEnergy(), player.getMaxEnergy(), player.getCurrentHP(), player.getFinalHP(), player.getCurrentExp(), player.getNxtLvlExp(), player.getQuestPoints(), player.getGold());
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

    public boolean lvlUp(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        if(player.getCurrentExp()>=player.getNxtLvlExp()){
            player.setLvl(player.getLvl()+1);
            player.setCurrentExp(0);
            player.setNxtLvlExp((int) (100 * player.getLvl() * Math.pow(1.2, player.getLvl()) - 1));
            userRepository.save(user);
            return true;
        }
        return false;
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
