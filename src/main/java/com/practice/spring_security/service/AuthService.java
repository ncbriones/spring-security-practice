package com.practice.spring_security.service;

import com.practice.spring_security.dto.request.LoginRequest;
import com.practice.spring_security.dto.request.RegisterRequest;
import com.practice.spring_security.dto.response.LoginResponse;
import com.practice.spring_security.dto.response.RegisterResponse;
import com.practice.spring_security.entity.Role;
import com.practice.spring_security.entity.User;
import com.practice.spring_security.repository.UserRepository;
import com.practice.spring_security.security.jwt.JwtService;
import com.practice.spring_security.security.user.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        Authentication unauthenticatedToken = new UsernamePasswordAuthenticationToken(
            request.email(),
            request.password()
        );

        Authentication authenticatedToken = authenticationManager.authenticate(unauthenticatedToken);

        CustomUserDetails userDetails = (CustomUserDetails) authenticatedToken.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(
                token,
                "Bearer",
                userDetails.getUsername(),
                userDetails.getRole()
        );
    }

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.STUDENT);

        userRepository.save(user);
        String token = jwtService.generateToken(new CustomUserDetails(user));

        return new RegisterResponse(
                token,
                "Bearer"
        );
    }
}
