package com.rahat.health_tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LogInResponseDto {
    private String token;

    private String tokenType;

    private long expiresIn;

    private String refreshToken;

    private long refreshExpiresIn;
}
