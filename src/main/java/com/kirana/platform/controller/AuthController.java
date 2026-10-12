package com.kirana.platform.controller;

import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirana.platform.dto.AuthRequest;
import com.kirana.platform.dto.AuthResponse;
import com.kirana.platform.dto.RegisterRequest;
import com.kirana.platform.model.User;
import com.kirana.platform.repository.UserRepository;
import com.kirana.platform.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        
        

        User user = new User(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getRole());
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent() && passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            User user = userOpt.get();
            
            // Convert role to String using .name() if it's an enum, or pass it directly if it's already a String
            String roleStr = user.getRole() != null ? user.getRole().toString() : "CUSTOMER";
            
            String token = jwtUtil.generateToken(user.getEmail(), roleStr);
            return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), roleStr));
        }

        return ResponseEntity.status(401).body("Error: Invalid email or password!");
    }
}