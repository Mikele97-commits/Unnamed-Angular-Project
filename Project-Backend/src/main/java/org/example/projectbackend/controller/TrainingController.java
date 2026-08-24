package org.example.projectbackend.controller;

import org.example.projectbackend.dto.TrainingAddDto;
import org.example.projectbackend.dto.TrainingDto;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.UserRepository;
import org.example.projectbackend.service.TrainingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TrainingController {
    private final TrainingService trainingService;
    private final UserRepository userRepository;


    public TrainingController(TrainingService trainingService,  UserRepository userRepository ) {
        this.trainingService = trainingService;
        this.userRepository = userRepository;
    }

    @GetMapping("/training")
    public TrainingDto getTraining(@AuthenticationPrincipal UserDetails userDetails) {
        return trainingService.getTrainingStats(userDetails.getUsername());
    }

    @PostMapping("/training/add")
    public TrainingDto addTraining(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TrainingAddDto trainingAddDto) {
        System.out.println(userDetails.getUsername());
        System.out.println(trainingAddDto.stat());
        return trainingService.increaseStat(userDetails.getUsername(), trainingAddDto.stat());
    }
}
