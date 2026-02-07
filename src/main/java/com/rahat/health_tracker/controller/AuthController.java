package com.rahat.health_tracker.controller;


import com.rahat.health_tracker.dto.request.user.LogInRequestDto;
import com.rahat.health_tracker.dto.request.user.RefreshTokenDto;
import com.rahat.health_tracker.dto.request.user.UserRegistrationRequest;
import com.rahat.health_tracker.dto.response.LogInResponseDto;
import com.rahat.health_tracker.dto.response.UserResponseDto;
import com.rahat.health_tracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationRequest request) {
        UserResponseDto response = authService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> login( @RequestBody LogInRequestDto request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LogInResponseDto> refreshToken(
            @Valid @RequestBody RefreshTokenDto request) {
        LogInResponseDto response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
