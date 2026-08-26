package org.example.projectbackend.service;

import org.example.projectbackend.dto.TopDivDto;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    UserRepository userRepository;

    public GameService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TopDivDto giveTopDto(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        Player player = user.getPlayer();
        System.out.println("Taking data of player "+user.getUsername());
        return new TopDivDto(player.getCurrEnergy(), player.getMaxEnergy(), player.getCurrentHP(), player.getFinalHP(), player.getCurrentExp(), player.getNxtLvlExp(), player.getQuestPoints());
    }
}
