package com.practice.spring_security.controller;

import com.practice.spring_security.dto.request.LoginRequest;
import com.practice.spring_security.dto.request.RegisterRequest;
import com.practice.spring_security.dto.response.LoginResponse;
import com.practice.spring_security.dto.response.RegisterResponse;
import com.practice.spring_security.entity.Role;
import com.practice.spring_security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok().body(response);
    }



}
