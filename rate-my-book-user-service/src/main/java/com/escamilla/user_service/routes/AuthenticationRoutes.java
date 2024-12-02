package com.escamilla.user_service.routes;

import com.escamilla.user_service.payload.request.SignUpRequest;
import com.escamilla.user_service.payload.response.JwtAuthResponse;
import com.escamilla.user_service.services.AuthService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationRoutes {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthResponse> signUp(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @PostMapping("/sigin")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authService.signUp(request));
    }
}
