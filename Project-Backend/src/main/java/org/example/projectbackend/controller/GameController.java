package org.example.projectbackend.controller;


import org.example.projectbackend.dto.TopDivDto;
import org.example.projectbackend.service.GameService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {
    GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping ("/topDiv")
    public TopDivDto topDiv(@AuthenticationPrincipal String username) {
        return gameService.giveTopDto(username);
    }



}
