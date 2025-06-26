package com.beci.product_service.controller;

import com.beci.product_service.dto.AuthRequest;
import com.beci.product_service.dto.AuthResponse;
import com.beci.product_service.dto.UserRequest;
import com.beci.product_service.dto.UserResponse;
import com.beci.product_service.mapper.UserMapper;
import com.beci.product_service.model.User;
import com.beci.product_service.repository.UserRepository;
import com.beci.product_service.security.JwtUtil;
import com.beci.product_service.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserMapper userMapper;

    @PostMapping("/register")
    public String register(@RequestBody UserRequest request) {
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Returnăm token + datele userului
        User user = (User) userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(user);
        UserResponse userResponse = userMapper.toResponse(user);

        return new AuthResponse(token, userResponse);
    }
}
