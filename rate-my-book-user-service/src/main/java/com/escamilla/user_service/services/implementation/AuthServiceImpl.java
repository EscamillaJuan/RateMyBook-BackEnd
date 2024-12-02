package com.escamilla.user_service.services.implementation;

import com.escamilla.user_service.models.entity.Role;
import com.escamilla.user_service.models.entity.UserEntity;
import com.escamilla.user_service.models.repository.UserRepository;
import com.escamilla.user_service.payload.request.LoginRequest;
import com.escamilla.user_service.payload.request.RegisterRequest;
import com.escamilla.user_service.payload.response.JwtAuthResponse;
import com.escamilla.user_service.services.AuthService;
import com.escamilla.user_service.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return JwtAuthResponse.builder()
                    .token(null)
                    .message("Email already registered")
                    .object(HttpStatus.CONFLICT)
                    .build();
        }
        var user = UserEntity.builder().firstName(request.getName())
                .lastName(request.getLastName())
                .userName(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder()
                .token(jwt)
                .message("User registered")
                .object(HttpStatus.CREATED)
                .build();
    }

    @Override
    public JwtAuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            return JwtAuthResponse.builder()
                    .token(null)
                    .message("Email or password incorrect")
                    .object(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        var user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            return JwtAuthResponse.builder()
                    .token(null)
                    .message("Email or password incorrect")
                    .object(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        var jwt = jwtService.generateToken(user.get());
        return JwtAuthResponse.builder()
                .token(jwt)
                .message("Authentication successful")
                .object(HttpStatus.OK)
                .build();
    }
}
