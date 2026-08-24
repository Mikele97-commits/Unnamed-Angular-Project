package org.example.projectbackend.service;

import jakarta.transaction.Transactional;
import org.example.projectbackend.dto.TrainingDto;
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

    public TrainingDto getTrainingStats(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        return new TrainingDto(player.getStrength(), player.getEndurance(), player.getPerception(), player.getSpeed(), player.getDexterity(), player.getLuck());
    }

    @Transactional
    public TrainingDto increaseStat(String username, String stat) {
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        switch (stat) {
            case "strength":
                System.out.println("adding strength");
                player.setStrength(player.getStrength() + 1);
                System.out.println("new strength added: " + player.getStrength());
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
        return new TrainingDto(player.getStrength(), player.getEndurance(), player.getPerception(), player.getSpeed(), player.getDexterity(), player.getLuck());
    }

}
