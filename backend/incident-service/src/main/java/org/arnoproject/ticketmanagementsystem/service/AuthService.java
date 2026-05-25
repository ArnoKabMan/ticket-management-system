package org.arnoproject.ticketmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.arnoproject.ticketmanagementsystem.dto.AuthResponse;
import org.arnoproject.ticketmanagementsystem.dto.LoginRequest;
import org.arnoproject.ticketmanagementsystem.dto.RegisterRequest;
import org.arnoproject.ticketmanagementsystem.entity.User;
import org.arnoproject.ticketmanagementsystem.enums.UserStatus;
import org.arnoproject.ticketmanagementsystem.repository.UserRepository;
import org.arnoproject.ticketmanagementsystem.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// service/AuthService.java
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(user), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return new AuthResponse(jwtService.generateToken(user), user.getRole().name());
    }
}
