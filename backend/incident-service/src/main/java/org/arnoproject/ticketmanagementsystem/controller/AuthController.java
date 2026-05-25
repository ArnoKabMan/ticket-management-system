package org.arnoproject.ticketmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.arnoproject.ticketmanagementsystem.dto.AuthResponse;
import org.arnoproject.ticketmanagementsystem.dto.LoginRequest;
import org.arnoproject.ticketmanagementsystem.dto.RegisterRequest;
import org.arnoproject.ticketmanagementsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// controller/AuthController.java
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }


}