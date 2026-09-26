package org.example.projectbackend.service;

import org.example.projectbackend.entity.FightResult;
import org.example.projectbackend.entity.Monster;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.logs.FightLogEntry;
import org.example.projectbackend.repository.PvMRepository;
import org.example.projectbackend.repository.MonsterRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FightService {
    MonsterRepository monsterRepository;
    UserRepository userRepository;
    PvMRepository pvMRepository;
    GameService gameService;
    public FightService(GameService gameService, MonsterRepository monsterRepository, UserRepository userRepository,PvMRepository pvMRepository) {
        this.monsterRepository = monsterRepository;
        this.userRepository = userRepository;
        this.pvMRepository = pvMRepository;
        this.gameService = gameService;
    }
    public Monster createMonster(String monsterName){
        Monster monster = new Monster();
        Monster template = monsterRepository.getMonsterByName(monsterName);

        BeanUtils.copyProperties(template,monster);
        monster.setId(null);
        monster = randomizeStats(monster);
        return monster;
    }
    public double createRandNumber(){
        double base = Math.random();
        base = base*0.4;
        base=base-0.2;
        return base;
    }
    public Monster randomizeStats(Monster monster){


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

    public FightResult createPvM(String username, String monsterName){
        FightResult fightResult = new FightResult();
        fightResult.setUsername(username);
        fightResult.setMonsterName(monsterName);
        fightResult.setFoughtAt(LocalDateTime.now().toString());

        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        Monster monster = createMonster(monsterName);
        int playerPoints=0;
        int monsterPoints=0;
        List<Integer> pointsList = new ArrayList<>();
        pointsList.add(playerPoints);
        pointsList.add(monsterPoints);
        double secondHitP=0;
        double secondHitM=0;
        List<Double> secondHitList=new ArrayList<>();
        secondHitList.add(secondHitP);
        secondHitList.add(secondHitM);
        double secondHitPIncrease=((double)(player.getSpeed()-monster.getSpeed())/(double)monster.getSpeed());
        double secondHitMIncrease=((double)(monster.getSpeed()-player.getSpeed())/(double)player.getSpeed());
        List<FightLogEntry> logEntries = new ArrayList<>();
        for(int i=0; i<10;i++){
            if(round(i+1,user,monster,secondHitList, logEntries, pointsList)){
                break;
            }
            secondHitList.set(0, secondHitList.get(0)+secondHitPIncrease);
            secondHitList.set(1, secondHitList.get(1)+secondHitMIncrease);
        }
        fightResult.setLog(logEntries);
        if(player.getCurrentHP()>0&&monster.getHp()>0){
            if(pointsList.get(0)>=pointsList.get(1)){
                fightResult.setPlayerWon(true);
            }else{
                fightResult.setPlayerWon(false);
            }
        }else{
            if(player.getCurrentHP()<=0){
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
        }
        userRepository.save(user);
        pvMRepository.save(fightResult);
        return fightResult;
    }

    public boolean round(int round, User user, Monster monster, List<Double> secondHitList, List<FightLogEntry> logEntries, List<Integer> pointsList){
        Player player = user.getPlayer();
        FightLogEntry fightLogEntry = new FightLogEntry();
        fightLogEntry.setRound(round);
        fightLogEntry.setActor(player.getUser().getUsername());
        //Check if double hit lands
        int times=1;
        if(secondHitList.get(0)>=1){
            fightLogEntry.setDoubleHit("Got double hit");
            times++;
            secondHitList.set(0,secondHitList.get(0)-1);
        }
        //Player starts with attack
        for(int i=1;i<=times;i++) {
            if (checkHit(player.getPerception(), monster.getDexterity(), player.getLvl() - monster.getLevel())) {
                int[] damages=gameService.calculateDmg(user.getUsername());
                int monsterArmor= monster.getArmor();
                monster.setHp(monster.getHp() - calculateDmg(damages, monsterArmor));
                pointsList.set(0, pointsList.get(0) + calculateDmg(damages, monsterArmor));
                if(i!=2) {
                    fightLogEntry.setMessage("You hit for " +calculateDmg(damages, monsterArmor)+ " hit points.");
                }else {
                    fightLogEntry.setDoubleHitMessage("You hit for " +calculateDmg(damages, monsterArmor)+ " hit points.");
                }
            }else {
                if(i!=2) {
                    fightLogEntry.setMessage("You missed");
                }else{
                    fightLogEntry.setDoubleHitMessage("You missed second hit");
                }
            }
        }

        //Check if monster defeated
        if(monster.getHp()<=0){
            fightLogEntry.setEndOfFight(monster.getName() + " dies. You win!");
            logEntries.add(fightLogEntry);
            return true;
        }
        logEntries.add(fightLogEntry);

        fightLogEntry = new FightLogEntry();
        fightLogEntry.setRound(round);
        fightLogEntry.setActor(monster.getName());
        //Monster turn
        times=1;
        if(secondHitList.get(1)>=1){
            times++;
            secondHitList.set(1,secondHitList.get(1)-1);
            fightLogEntry.setDoubleHit(monster.getName()+" got double hit");
        }
        for(int i=1;i<=times;i++) {
            if (checkHit(monster.getPerception(), player.getDexterity(), monster.getLevel() - player.getLvl())) {
                int[] damages = gameService.calculateMonsterDmg(monster.getName());
                int playerArmor= gameService.armor(user.getUsername());
                int finalDmg=calculateDmg(damages, monster.getArmor());
                player.setCurrentHP(player.getCurrentHP() - finalDmg);
                pointsList.set(1, pointsList.get(1) + finalDmg);
                if(i!=2) {
                    fightLogEntry.setMessage(monster.getName() + " hits for " + finalDmg + " hit points.");
                }else{
                    fightLogEntry.setDoubleHitMessage(monster.getName() + " hits for " + finalDmg + " hit points.");
                }
            }else{
                if(i!=2) {
                    fightLogEntry.setMessage(monster.getName() + " missed");
                }else{
                    fightLogEntry.setDoubleHitMessage(monster.getName()+" missed second hit");
                }
            }
        }

        if(player.getCurrentHP()<=0){
            fightLogEntry.setEndOfFight("You die.");
        }
        logEntries.add(fightLogEntry);
        return player.getCurrentHP() <= 0;
    }

    private boolean checkHit(int attackerPer, int defenderDex, int lvlDiff){
        double hitChance = 75.0;
        hitChance += attackerPer * 0.4;
        hitChance -= defenderDex * 0.4;
        hitChance += lvlDiff * 2.5;
        hitChance = Math.max(20.0, Math.min(95.0, hitChance));
        double roll = Math.random() * 100;
        return roll < hitChance;
    }

    private int calculateDmg(int[] minMax, int armor){
        int min=minMax[0];
        int max=minMax[1];

        Random rand = new Random();
        int dmg= rand.nextInt((max-min)+1)+min;
        float finalDmg=(float)dmg*(100/(100+(float)armor));
        return (int)finalDmg;
    }



}
