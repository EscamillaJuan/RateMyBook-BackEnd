package com.escamilla.user_service.services;

import com.escamilla.user_service.payload.request.SignUpRequest;
import com.escamilla.user_service.payload.response.JwtAuthResponse;

public interface AuthService {
    JwtAuthResponse signUp(SignUpRequest request);
    JwtAuthResponse signIn(SignUpRequest request);
}
