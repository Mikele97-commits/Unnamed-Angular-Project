package org.example.projectbackend.service;

import jakarta.transaction.Transactional;
import org.example.projectbackend.dto.TrainingDto;
import org.example.projectbackend.dto.TrainingResponseDto;
import org.example.projectbackend.entity.Player;

import org.springframework.stereotype.Service;

@Service
public class TrainingService {
    PlayerService playerService;

    public TrainingService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public TrainingResponseDto getTrainingStats(String username) {
        Player player= playerService.getPlayer(username);
        return new TrainingResponseDto("Data loaded", true,new TrainingDto(player.getStrength(), player.getEndurance(), player.getPerception(), player.getSpeed(), player.getDexterity(), player.getLuck()));
    }

    @Transactional
    public TrainingResponseDto increaseStat(String username, String stat) {
        Player player = playerService.getPlayer(username);
        if(player.getCurrEnergy()>=5){
            player.setCurrEnergy(player.getCurrEnergy()-5);
        }else{
            return new TrainingResponseDto("Not enough energy!",false,null);
        }
        switch (stat) {
            case "strength":
                player.setStrength(player.getStrength() + 1);
                break;
            case "endurance":
                player.setEndurance(player.getEndurance() + 1);
                playerService.maxHPRecalculation(player);
                break;
            case "perception":
                player.setPerception(player.getPerception() + 1);
                break;
            case "speed":
                player.setSpeed(player.getSpeed() + 1);
                break;
            case "dexterity":
                player.setDexterity(player.getDexterity() + 1);
                break;
            case "luck":
                player.setLuck(player.getLuck() + 1);
                break;
        }
        return new TrainingResponseDto("Training successful", true,new TrainingDto(player.getStrength(), player.getEndurance(), player.getPerception(), player.getSpeed(), player.getDexterity(), player.getLuck()));
    }

}
