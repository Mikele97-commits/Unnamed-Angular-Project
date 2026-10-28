package org.example.projectbackend.service;

import org.example.projectbackend.entity.Combatant;
import org.example.projectbackend.entity.FightResult;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.RoundResult;
import org.example.projectbackend.logs.FightLogEntry;
import org.example.projectbackend.repository.PlayerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PvPService {

    private final PlayerService playerService;
    FightService fightService;
    PlayerRepository playerRepository;

    public PvPService(FightService fightService, PlayerRepository playerRepository, PlayerService playerService) {

        this.playerRepository = playerRepository;
        this.fightService = fightService;
        this.playerService = playerService;
    }



    public FightResult createPVP(String attackerUsername, String defenderUsername){
        Player attacker= playerService.getPlayer(attackerUsername);
        Player defender= playerService.getPlayer(defenderUsername);
        Combatant combatant1=fightService.playerToCombatant(attacker);
        Combatant combatant2=fightService.playerToCombatant(defender);

        FightResult fightResult = new FightResult();
        FightResult loseResult = new FightResult();
        fightResult.setAttacker(attackerUsername);
        fightResult.setDefender(defenderUsername);
        fightResult.setFoughtAt(LocalDateTime.now().toString());


        List<FightLogEntry> logEntries = new ArrayList<>();

        List<Integer> pointsList=fightService.initializePointsList();
        List<Double> secondHitList=fightService.initializeSecondHitList();
        double secondHitAIncrease=((double)(attacker.getSpeed()-defender.getSpeed())/(double)defender.getSpeed());
        double secondHitDIncrease=((double)(defender.getSpeed()-attacker.getSpeed())/(double)attacker.getSpeed());



        Player winner=null;
        Player loser=null;
        boolean attackerLost=false;
        int afterFightHpAttacker=0;
        int afterFightHpDefender=0;

        for(int i=1;i<=10;i++){
            RoundResult result=fightService.round(i,combatant1, combatant2, secondHitList, logEntries, pointsList);
            afterFightHpAttacker=result.attackerHP;
            afterFightHpDefender=result.defenderHP;
            if(result.endOfFight){
                if(afterFightHpAttacker>afterFightHpDefender){
                    winner=attacker;
                    loser=defender;
                }else{
                    winner=defender;
                    loser=attacker;
                }
                break;
            }
        }
        if(winner==null&&loser==null){
            if(pointsList.get(0)>pointsList.get(1)){
                winner=attacker;
                loser=defender;
            }else if(pointsList.get(1)>=pointsList.get(0)){
                loser=attacker;
                winner=defender;
            }
        }
        attacker.setCurrentHP(afterFightHpAttacker);
        defender.setCurrentHP(afterFightHpDefender);
        fightResult.setLog(logEntries);
        fightResult.setPlayerWon(true);

        if(winner==attacker){
            fightResult.setGoldGained(robDefender(attacker,defender));
        }

        BeanUtils.copyProperties(fightResult,loseResult);
        loseResult.setPlayerWon(false);

        fightService.setResult(fightResult, winner, loser);
        playerRepository.save(attacker);
        playerRepository.save(defender);

        return fightResult;
    }


    public int robDefender(Player winner, Player loser){
        int robbedGold=loser.getGold()/10;
        loser.setGold(loser.getGold()-robbedGold);
        winner.setGold(winner.getGold()+robbedGold);
        return robbedGold;
    }

}
