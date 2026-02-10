package com.rahat.health_tracker.controller;


import com.rahat.health_tracker.dto.request.auth.DoctorRegistrationRequest;
import com.rahat.health_tracker.dto.request.auth.LogInRequestDto;
import com.rahat.health_tracker.dto.request.auth.RefreshTokenDto;
import com.rahat.health_tracker.dto.request.auth.UserRegistrationRequest;
import com.rahat.health_tracker.dto.response.DoctorResponseDto;
import com.rahat.health_tracker.dto.response.LogInResponseDto;
import com.rahat.health_tracker.dto.response.UserResponseDto;
import com.rahat.health_tracker.service.auth.AuthDoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth/doctor")
@RequiredArgsConstructor
public class AuthDoctorController {
    private final AuthDoctorService authDoctorService;


    @PostMapping("/register")
    public ResponseEntity<DoctorResponseDto> register(@Valid @RequestBody DoctorRegistrationRequest request) {
        DoctorResponseDto response = authDoctorService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> login(@Valid @RequestBody LogInRequestDto request) {
        return ResponseEntity.ok(authDoctorService.loginUser(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LogInResponseDto> refreshToken(
            @Valid @RequestBody RefreshTokenDto request) {
        LogInResponseDto response = authDoctorService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
