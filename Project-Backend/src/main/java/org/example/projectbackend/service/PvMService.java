package org.example.projectbackend.service;

import org.example.projectbackend.entity.*;
import org.example.projectbackend.entity.items.ItemTemplate;
import org.example.projectbackend.logs.FightLogEntry;
import org.example.projectbackend.repository.ItemTemplateRepository;
import org.example.projectbackend.repository.MonsterRepository;
import org.example.projectbackend.repository.PlayerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PvMService {

    PlayerService playerService;
    FightService fightService;
    InventoryService inventoryService;
    MonsterRepository  monsterRepository;
    PlayerRepository playerRepository;
    ItemTemplateRepository itemTemplateRepository;

    public PvMService(PlayerRepository playerRepository,ItemTemplateRepository itemTemplateRepository, MonsterRepository monsterRepository,InventoryService inventoryService,PlayerService playerService, FightService fightService) {
        this.fightService = fightService;
        this.itemTemplateRepository = itemTemplateRepository;
        this.playerRepository = playerRepository;
        this.monsterRepository = monsterRepository;
        this.playerService = playerService;
        this.inventoryService = inventoryService;

    }

    public Player isValidExpedition(String username){
        Player player= playerService.getPlayer(username);
        if(player.getQuestPoints()<=0){
            return null;
        }
        if(player.getCurrentHP()<20){
            return null;
        }
        player.setQuestPoints(player.getQuestPoints()-1);
        return player;
    }

    public FightResult createPvM(String username, String monsterName){
        //Setting basic Fight Log info
        FightResult fightResult = new FightResult();
        fightResult.setAttacker(username);
        fightResult.setDefender(monsterName);
        fightResult.setFoughtAt(LocalDateTime.now().toString());
        //Setting player and monster
        Player player = playerService.getPlayer(username);
        Monster monster = createMonster(monsterName);
        //Changing player and monster to "Combatants"
        Combatant cPlayer=fightService.playerToCombatant(player);
        Combatant cMonster=fightService.monsterToCombatant(monster);

        //Initialization of points and double hit lists
        List<Integer> pointsList=fightService.initializePointsList();
        List<Double> secondHitList=fightService.initializeSecondHitList();
        double secondHitPIncrease=((double)(player.getSpeed()-monster.getSpeed())/(double)monster.getSpeed());
        double secondHitMIncrease=((double)(monster.getSpeed()-player.getSpeed())/(double)player.getSpeed());


        List<FightLogEntry> logEntries = new ArrayList<>();
        int afterFightHp=0;
        for(int i=1; i<=10;i++){
            RoundResult result=fightService.round(i,cPlayer,cMonster,secondHitList, logEntries, pointsList);
            afterFightHp= result.attackerHP;
            if(result.endOfFight){
                break;
            }
            secondHitList.set(0, secondHitList.get(0)+secondHitPIncrease);
            secondHitList.set(1, secondHitList.get(1)+secondHitMIncrease);
        }
        //Saving all round entries in fight result
        fightResult.setLog(logEntries);
        //Setting player's HP up to date after fight
        player.setCurrentHP(afterFightHp);
        //If no combatant died during fight
        if(player.getCurrentHP()>0&&monster.getHp()>0){
            //If player has more or the same amount points as enemy (more dmg dealt), player wins
            if(pointsList.get(0)>=pointsList.get(1)){
                fightResult.setPlayerWon(true);
            }else{
                fightResult.setPlayerWon(false);
            }
        }else{
            //If one of combatants died, if player has <=0 hp, player loses
            if(player.getCurrentHP()<=0){
                //So it wouldn't be negative
                player.setCurrentHP(0);
                fightResult.setPlayerWon(false);
            }else{
                fightResult.setPlayerWon(true);
            }
        }

        if(fightResult.isPlayerWon()){
            player.setGold(player.getGold()+monster.getGoldReward());
            player.setCurrentExp(player.getCurrentExp()+monster.getExpReward());
            fightResult.setExpGained(monster.getExpReward());
            fightResult.setGoldGained(monster.getGoldReward());
            if(hasLoot()){
                fightResult.setLoot(inventoryService.addItem(username,randomizeLoot(monster)));
            }else {
                fightResult.setLoot(null);
            }
        }
        fightResult.setPlayer(player);
        playerRepository.save(player);
        return fightResult;
    }

    public Monster createMonster(String monsterName){
        Monster monster = new Monster();
        Monster template = monsterRepository.getMonsterByName(monsterName);
        BeanUtils.copyProperties(template,monster);
        monster.setId(null);
        //Setting every monster stat +/- 20% of its template stat
        monster.setStrength(monster.getStrength()+(int)((double)monster.getStrength()*createRandNumber()));
        monster.setEndurance(monster.getEndurance()+(int)((double)monster.getEndurance()*createRandNumber()));
        monster.setPerception(monster.getPerception()+(int)((double)monster.getPerception()*createRandNumber()));
        monster.setSpeed(monster.getSpeed()+(int)((double)monster.getSpeed()*createRandNumber()));
        monster.setDexterity(monster.getDexterity()+(int)((double)monster.getDexterity()*createRandNumber()));
        monster.setLuck(monster.getLuck()+(int)((double)monster.getLuck()*createRandNumber()));
        monster.setExpReward(monster.getExpReward()+(int)((double)monster.getExpReward()*createRandNumber()));
        monster.setGoldReward(monster.getGoldReward()+(int)((double)monster.getGoldReward()*createRandNumber()));
        return monster;
    }
    public double createRandNumber(){
        double base = Math.random();
        base = base*0.4;
        base=base-0.2;
        return base;
    }

    private boolean hasLoot() {
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
