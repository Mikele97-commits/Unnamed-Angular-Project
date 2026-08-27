package org.example.projectbackend.service;

import jakarta.transaction.Transactional;
import org.example.projectbackend.dto.TrainingDto;
import org.example.projectbackend.dto.TrainingResponseDto;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {
    UserRepository userRepository;

    public TrainingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TrainingResponseDto getTrainingStats(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        return new TrainingResponseDto("Data loaded", true,new TrainingDto(player.getStrength(), player.getEndurance(), player.getPerception(), player.getSpeed(), player.getDexterity(), player.getLuck()));
    }

    @Transactional
    public TrainingResponseDto increaseStat(String username, String stat) {
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
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
