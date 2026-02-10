package com.rahat.health_tracker.controller;


import com.rahat.health_tracker.dto.request.auth.LogInRequestDto;
import com.rahat.health_tracker.dto.request.auth.ParentRegistrationRequest;
import com.rahat.health_tracker.dto.request.auth.RefreshTokenDto;
import com.rahat.health_tracker.dto.response.LogInResponseDto;
import com.rahat.health_tracker.dto.response.ParentResponseDto;
import com.rahat.health_tracker.service.auth.AuthParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/parent")
public class AuthParentController {
    private final AuthParentService authParentService;

    @PostMapping("/register")
    public ResponseEntity<ParentResponseDto> register(@Valid @RequestBody ParentRegistrationRequest request) {
        ParentResponseDto response = authParentService.parentCompanyRegister(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> login( @RequestBody LogInRequestDto request) {
        return ResponseEntity.ok(authParentService.loginParentCompany(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LogInResponseDto> refreshToken(
            @Valid @RequestBody RefreshTokenDto request) {
        LogInResponseDto response = authParentService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
