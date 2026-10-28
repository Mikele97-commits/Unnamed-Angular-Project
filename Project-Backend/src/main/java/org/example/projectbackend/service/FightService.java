package org.example.projectbackend.service;

import org.example.projectbackend.entity.*;
import org.example.projectbackend.logs.FightLogEntry;
import org.example.projectbackend.repository.PlayerRepository;
import org.example.projectbackend.repository.PvMRepository;
import org.example.projectbackend.repository.MonsterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FightService {
    private final PlayerRepository playerRepository;
    InventoryService inventoryService;
    MonsterRepository monsterRepository;
    PvMRepository pvMRepository;
    GameService gameService;
    PlayerService playerService;
    public FightService(PlayerService playerService, GameService gameService, MonsterRepository monsterRepository, PvMRepository pvMRepository, InventoryService inventoryService, PlayerRepository playerRepository) {
        this.monsterRepository = monsterRepository;
        this.pvMRepository = pvMRepository;
        this.gameService = gameService;
        this.inventoryService = inventoryService;
        this.playerService = playerService;
        this.playerRepository = playerRepository;
    }

    public Combatant monsterToCombatant(Monster monster){
        return new Combatant(monster.getName(),monster.getLevel(),monster.getPerception(),monster.getDexterity(), monster.getSpeed(), monster.getLuck(),monster.getArmor(), monster.getHp(), new int[] {monster.getMinDamage(),monster.getMaxDamage()});
    }
    public Combatant playerToCombatant(Player player){
        return new Combatant(player.getUser().getUsername(), player.getLvl(), player.getPerception(), player.getDexterity(), player.getSpeed(), player.getLuck(), player.getArmor(), player.getCurrentHP(), playerService.calculateDmg(player));
    }




    public RoundResult round(int round, Combatant attacker, Combatant defender, List<Double> secondHitList, List<FightLogEntry> logEntries, List<Integer> pointsList){
        //Setting round logs info
        FightLogEntry fightLogEntry = new FightLogEntry();
        fightLogEntry.setRound(round);
        fightLogEntry.setActor(attacker.name);

        //Check if there's double hit
        int times=1;
        if(secondHitList.get(0)>=1){
            fightLogEntry.setDoubleHit("Got double hit");
            times++;
            secondHitList.set(0,secondHitList.get(0)-1);
        }

        //Attacker starts with attack. "i" equals number of attacks, dependent on double hit check
        for(int i=1;i<=times;i++) {
            if (checkHit(attacker.perception, defender.dexterity, attacker.level - defender.level)) {
                String critHitMessage="";
                //Hit connected. Calculating damage to defender
                int attackerDamage=calculateDmg(attacker.damageRange, defender.armor);
                //Check for crit
                if(checkCrit(attacker, defender)){
                    critHitMessage="Critical hit! ";
                    attackerDamage=attackerDamage*2;
                }
                //Subtracting said damage from defender HP
                defender.currentHp=defender.currentHp-attackerDamage;
                //Adding the damage to attackers points
                pointsList.set(0, pointsList.get(0) + attackerDamage);
                if(i!=2) {
                    //When it was attackers first strike of the round
                    fightLogEntry.setMessage(critHitMessage+ attacker.name + " hit for " +attackerDamage+ " hit points.");
                }else {
                    //When he rolled and hit the second one
                    fightLogEntry.setDoubleHitMessage(critHitMessage+ attacker.name + " hit for " +attackerDamage+ " hit points.");
                }
            }else {
                //Hit missed
                if(i!=2) {
                    //If was first hit
                    fightLogEntry.setMessage(attacker.name + " missed");
                }else{
                    //If was second hit
                    fightLogEntry.setDoubleHitMessage(attacker.name + " missed second hit");
                }
            }
        }

        //Check if defender defeated
        if(defender.currentHp<=0){
            fightLogEntry.setEndOfFight(defender.name + " dies. " + attacker.name +" win!");
            logEntries.add(fightLogEntry);
            return new RoundResult(true, attacker.currentHp, defender.currentHp);
        }
        //Saving log
        logEntries.add(fightLogEntry);

        //Creating new log for defenders turn
        fightLogEntry = new FightLogEntry();
        fightLogEntry.setRound(round);
        fightLogEntry.setActor(defender.name);

        //Check if there's double hit
        times=1;
        if(secondHitList.get(1)>=1){
            times++;
            secondHitList.set(1,secondHitList.get(1)-1);
            fightLogEntry.setDoubleHit(defender.name+" got double hit");
        }

        //Defender attacks. "i" equals number of attacks, dependent on double hit check
        for(int i=1;i<=times;i++) {
            if (checkHit(defender.perception, attacker.dexterity, defender.level - attacker.level)) {
                String critHitMessage="";
                int defenderDamage=calculateDmg(defender.damageRange, attacker.armor);
                if(checkCrit(defender, attacker)){
                    critHitMessage="Critical hit! ";
                    defenderDamage=defenderDamage*2;
                }
                attacker.currentHp=attacker.currentHp-defenderDamage;
                pointsList.set(1, pointsList.get(1) + defenderDamage);
                if(i!=2) {
                    fightLogEntry.setMessage(critHitMessage+defender.name + " hits for " + defenderDamage + " hit points.");
                }else{
                    fightLogEntry.setDoubleHitMessage(critHitMessage+defender.name + " hits for " + defenderDamage + " hit points.");
                }
            }else{
                if(i!=2) {
                    fightLogEntry.setMessage(defender.name + " missed");
                }else{
                    fightLogEntry.setDoubleHitMessage(defender.name+" missed second hit");
                }
            }
        }

        //Check if attacker defeated
        if(attacker.currentHp<=0){
            fightLogEntry.setEndOfFight(attacker.name + " dies. " + defender.name +" win!");
            logEntries.add(fightLogEntry);
            return new RoundResult(true, attacker.currentHp, defender.currentHp);
        }
        logEntries.add(fightLogEntry);
        return new RoundResult(false, attacker.currentHp, defender.currentHp);
    }

    public boolean checkHit(int attackerPer, int defenderDex, int lvlDiff){
        double hitChance = 75.0;
        hitChance += attackerPer * 0.4;
        hitChance -= defenderDex * 0.4;
        hitChance += lvlDiff * 2.5;
        hitChance = Math.max(20.0, Math.min(95.0, hitChance));
        double roll = Math.random() * 100;
        return roll < hitChance;
    }

    public boolean checkCrit(Combatant attacker, Combatant defender) {
        double critChance = 5.0;

        critChance += attacker.luck * 0.3;
        critChance -= defender.luck * 0.1;



        critChance = Math.max(1.0, Math.min(50.0, critChance));

        double roll = Math.random() * 100;
        return roll < critChance;
    }

    public int calculateDmg(int[] minMax, int armor){
        int min=minMax[0];
        int max=minMax[1];

        Random rand = new Random();
        int dmg= rand.nextInt((max-min)+1)+min;
        float finalDmg=(float)dmg*(100/(100+(float)armor));
        return (int)finalDmg;
    }

    public List<Double> initializeSecondHitList(){
        List<Double> secondHitList=new ArrayList<>();
        secondHitList.add(0.0);
        secondHitList.add(0.0);
        return secondHitList;
    }
    public List<Integer> initializePointsList(){
        List<Integer> pointsList = new ArrayList<>();
        pointsList.add(0);
        pointsList.add(0);
        return pointsList;
    }
    public void setResult(FightResult fightResult, Player winner, Player loser){
        fightResult.setPlayerWon(true);
        winner.getFightResults().add(fightResult);
        FightResult loseResult = new FightResult();
        BeanUtils.copyProperties(fightResult,loseResult);
        loseResult.setPlayerWon(false);
        loseResult.setId(null);
        loser.getFightResults().add(loseResult);
        fightResult.setPlayer(winner);
        loseResult.setPlayer(loser);
    }
}

