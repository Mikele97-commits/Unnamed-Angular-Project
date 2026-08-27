package org.example.projectbackend.service;
import org.example.projectbackend.dto.AnswerDto;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthService authService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.jwtService=jwtService;
    }

    public AnswerDto addUser(User user){
        if(!authService.passwordValid(user.getPassword())){
            return new AnswerDto("Invalid password", false);
        }
        if(userRepository.existsByUsername(user.getUsername())){
            return new AnswerDto("Username already exists", false);
        }
        if(userRepository.existsByEmail(user.getEmail())){
            return new AnswerDto("Email already registered", false);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println("User added successfully");
        return new AnswerDto("User has been registered", true);
    }

    public User createUser(String username, String password, String email){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        Player player = new Player();
        user.setPlayer(player);
        return user;
    }

    public AnswerDto loginUser(String username, String password){
        if(username.isEmpty() || password.isEmpty()||username.isBlank()||password.isBlank()){
            return new AnswerDto("Username or Password empty", false);
        }
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){
            return new AnswerDto("Username doesn't exist", false);
        }

        if(passwordEncoder.matches(password,user.getPassword())){
            System.out.println("generating token for user " + user.getUsername());
            String token=jwtService.generateToken(user);
            return new AnswerDto("", true, token);
        }
        return new AnswerDto("Incorrect password", false);
    }

}


