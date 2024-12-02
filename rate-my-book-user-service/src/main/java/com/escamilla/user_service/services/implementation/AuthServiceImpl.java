package com.escamilla.user_service.services.implementation;

import com.escamilla.user_service.models.entity.Role;
import com.escamilla.user_service.models.entity.UserEntity;
import com.escamilla.user_service.models.repository.UserRepository;
import com.escamilla.user_service.payload.request.SignUpRequest;
import com.escamilla.user_service.payload.response.JwtAuthResponse;
import com.escamilla.user_service.services.AuthService;
import com.escamilla.user_service.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public JwtAuthResponse signUp(SignUpRequest request) {
        var user = UserEntity.builder().name(request.getName())
                .lastName(request.getLastName())
                .userName(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .dateBirth(request.getDateBirth())
                .favoriteTopics(request.getFavoriteTopics())
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthResponse signIn(SignUpRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(jwt).build();
    }
}
