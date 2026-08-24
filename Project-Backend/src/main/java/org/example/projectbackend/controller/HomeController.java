package org.example.projectbackend.controller;

import org.example.projectbackend.dto.LoginDto;
import org.example.projectbackend.dto.AnswerDto;
import org.example.projectbackend.dto.RegisterDto;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public AnswerDto login(@RequestBody LoginDto loginDto) {
        return userService.loginUser(loginDto.username(), loginDto.password());
    }

    @PostMapping("/register")
    public AnswerDto register(@RequestBody RegisterDto RegisterDto) {

        User user = userService.createUser(RegisterDto.username(), RegisterDto.password(), RegisterDto.email());
        return userService.addUser(user);
    }



}



