package org.example.projectbackend.service;

import org.example.projectbackend.entity.FightResult;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.logs.FightLogEntry;
import org.example.projectbackend.repository.PlayerRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PvPService {

    private final PlayerService playerService;
    UserRepository userRepository;
    FightService fightService;
    PlayerRepository playerRepository;

    public PvPService(FightService fightService, UserRepository userRepository, PlayerRepository playerRepository, PlayerService playerService) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.fightService = fightService;
        this.playerService = playerService;
    }

    public void createPVP(String attackerUsername, String defenderUsername){
        User user1=userRepository.findByUsername(attackerUsername).orElse(null);
        User user2=userRepository.findByUsername(defenderUsername).orElse(null);
        Player attacker= user1.getPlayer();
        Player defender= user2.getPlayer();

        FightResult fightResult = new FightResult();
        fightResult.setAttacker(attackerUsername);
        fightResult.setDefender(defenderUsername);
        fightResult.setFoughtAt(LocalDateTime.now().toString());


        List<FightLogEntry> logEntries = new ArrayList<>();

        List<Integer> pointsList=fightService.initializePointsList();
        List<Double> secondHitList=fightService.initializeSecondHitList();
        double secondHitAIncrease=((double)(attacker.getSpeed()-defender.getSpeed())/(double)defender.getSpeed());
        double secondHitDIncrease=((double)(defender.getSpeed()-attacker.getSpeed())/(double)attacker.getSpeed());



        for(int i=1; i<=20;i++){
            Player winner=round(attackerUsername,defenderUsername,i, attacker,defender,pointsList, secondHitList, logEntries);
            if(winner!=null){
                fightResult.setPlayerWon(true);
                winner.getFightResults().add(fightResult);
                FightResult loseResult = new FightResult();
                BeanUtils.copyProperties(fightResult,loseResult);
                loseResult.setPlayerWon(false);
                if(winner==attacker){
                    defender.getFightResults().add(loseResult);
                }else{
                    attacker.getFightResults().add(loseResult);
                }
            }
            secondHitList.set(0, secondHitList.get(0)+secondHitAIncrease);
            secondHitList.set(1, secondHitList.get(1)+secondHitDIncrease);
        }
    }

    public Player round(String attackerUsername, String defenderUsername, int round, Player attacker, Player defender, List<Integer> pointsList, List<Double> secondHitList, List<FightLogEntry> logEntries){
        int lvlDiffAttacker= attacker.getLvl()-defender.getLvl();
        int lvlDiffDefender= defender.getLvl()-attacker.getLvl();
        int[] attackerMinMax=playerService.calculateDmg(attacker);
        int[] defenderMinMax=playerService.calculateDmg(defender);

        FightLogEntry fightLogEntry = new FightLogEntry();
        fightLogEntry.setActor(attacker.getUser().getUsername());
        fightLogEntry.setRound(round);


        //Attacker turn
        if(fightService.checkHit(attacker.getPerception(),defender.getDexterity(),lvlDiffAttacker)){
            int dmg=fightService.calculateDmg(attackerMinMax,defender.getArmor());
            if(fightService.checkCrit(attacker,defender)){
                dmg=dmg*2;
            }
            pointsList.set(0, pointsList.get(0) + dmg);
            defender.setCurrentHP(defender.getCurrentHP()-dmg);
            fightLogEntry.setMessage(attackerUsername + " hits for " +dmg+ " hit points.");

        }
        if(secondHitList.get(0)>=1){
            int dmg=fightService.calculateDmg(attackerMinMax,defender.getArmor());
            if(fightService.checkCrit(attacker,defender)){
                dmg=dmg*2;
            }
            pointsList.set(0, pointsList.get(0) + dmg);
            defender.setCurrentHP(defender.getCurrentHP()-dmg);
            secondHitList.set(0, secondHitList.get(0)-1);
            fightLogEntry.setDoubleHitMessage("Double attack! " + attackerUsername + " hits for " +dmg+ " hit points.");

        }

        //Check if defender ded
        if(defender.getCurrentHP()<=1){
            defender.setCurrentHP(1);
            fightLogEntry.setEndOfFight(defenderUsername + " fainted. " + attackerUsername + " wins!");
            logEntries.add(fightLogEntry);
            return attacker;
        }
        //Defender turn
        if(fightService.checkHit(defender.getPerception(),attacker.getDexterity(),lvlDiffDefender)){
            int dmg=fightService.calculateDmg(defenderMinMax,attacker.getArmor());
            if(fightService.checkCrit(defender,attacker)){
                dmg=dmg*2;
            }
            pointsList.set(1, pointsList.get(1) + dmg);
            attacker.setCurrentHP(attacker.getCurrentHP()-dmg);
            fightLogEntry.setMessage(defenderUsername + " hits for " +dmg+ " hit points.");

        }
        if(secondHitList.get(1)>=1){
            int dmg=fightService.calculateDmg(defenderMinMax,attacker.getArmor());
            if(fightService.checkCrit(defender,attacker)){
                dmg=dmg*2;
            }
            pointsList.set(1, pointsList.get(1) + dmg);
            attacker.setCurrentHP(attacker.getCurrentHP()-dmg);
            secondHitList.set(1, secondHitList.get(1)-1);
            fightLogEntry.setDoubleHitMessage("Double attack! " + defenderUsername + " hits for " +dmg+ " hit points.");

        }
        if(attacker.getCurrentHP()<=1){
            attacker.setCurrentHP(1);
            fightLogEntry.setEndOfFight(attackerUsername + " fainted. " + defenderUsername + " wins!");
            logEntries.add(fightLogEntry);
            return defender;
        }
        logEntries.add(fightLogEntry);
        return null;
    }

}
