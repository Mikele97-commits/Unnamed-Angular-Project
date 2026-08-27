package org.example.projectbackend.controller;

import org.example.projectbackend.dto.TrainingAddDto;
import org.example.projectbackend.dto.TrainingResponseDto;
import org.example.projectbackend.service.TrainingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TrainingController {
    private final TrainingService trainingService;


    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/training")
    public TrainingResponseDto getTraining(@AuthenticationPrincipal String username) {
        return trainingService.getTrainingStats(username);
    }

    @PostMapping("/training/add")
    public TrainingResponseDto addTraining(@AuthenticationPrincipal String username, @RequestBody TrainingAddDto trainingAddDto) {
        return trainingService.increaseStat(username, trainingAddDto.stat());
    }
}
