package com.escamilla.user_service.services;

import com.escamilla.user_service.payload.request.LoginRequest;
import com.escamilla.user_service.payload.request.RegisterRequest;
import com.escamilla.user_service.payload.response.JwtAuthResponse;

public interface AuthService {
    JwtAuthResponse register(RegisterRequest request);
    JwtAuthResponse login(LoginRequest request);
}
