package com.rahat.health_tracker.controller;


import com.rahat.health_tracker.dto.request.auth.BranchRegistrationRequest;
import com.rahat.health_tracker.dto.request.auth.LogInRequestDto;
import com.rahat.health_tracker.dto.request.auth.RefreshTokenDto;
import com.rahat.health_tracker.dto.response.BranchResponseDto;
import com.rahat.health_tracker.dto.response.LogInResponseDto;
import com.rahat.health_tracker.service.auth.AuthBranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/branch")
public class AuthBranchController {
    private final AuthBranchService authBranchService;

    @PostMapping("/register")
    public ResponseEntity<BranchResponseDto> register(@Valid @RequestBody BranchRegistrationRequest request) {
        BranchResponseDto response = authBranchService.registerBranch(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> login(@Valid @RequestBody LogInRequestDto request) {
        return ResponseEntity.ok(authBranchService.loginBranch(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LogInResponseDto> refreshToken(
            @Valid @RequestBody RefreshTokenDto request) {
        LogInResponseDto response = authBranchService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
