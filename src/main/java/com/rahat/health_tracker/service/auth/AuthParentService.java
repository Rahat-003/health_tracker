package com.rahat.health_tracker.service.auth;

import com.rahat.health_tracker.enums.Role;
import com.rahat.health_tracker.security.JwtService;
import com.rahat.health_tracker.dto.request.auth.LogInRequestDto;
import com.rahat.health_tracker.dto.request.auth.ParentRegistrationRequest;
import com.rahat.health_tracker.dto.response.LogInResponseDto;
import com.rahat.health_tracker.dto.response.ParentResponseDto;
import com.rahat.health_tracker.entity.diagnostic_center.ParentCompany;
import com.rahat.health_tracker.entity.diagnostic_center.ParentRefreshToken;
import com.rahat.health_tracker.exception.auth.EmailAlreadyExistsException;
import com.rahat.health_tracker.exception.auth.EmailNotFoundException;
import com.rahat.health_tracker.exception.auth.IncorrectPasswordException;
import com.rahat.health_tracker.repository.ParentCompanyRepository;
import com.rahat.health_tracker.repository.ParentRefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthParentService {
    private final ParentCompanyRepository parentCompanyRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ParentRefreshTokenRepository parentRefreshTokenRepository;

    @Value("${jwt.access-token-expiry}")
    private Long accessTokenExpiry;

    @Value("${jwt.refresh-token-expiry}")
    private Long refreshTokenExpiry;

    public ParentResponseDto parentCompanyRegister(ParentRegistrationRequest request) {
        if (parentCompanyRepository.existsByEmail(request.getEmail())) {
//            System.out.println("email exists: " + request.getEmail());
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        ParentCompany parentCompany = ParentCompany.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .companyName(request.getCompanyName())
                .phoneNo(request.getPhoneNo())
                .isEnabled(true)
                .role(Role.COMPANY_ADMIN)
                .build();

        ParentCompany saved = parentCompanyRepository.save(parentCompany);

        return ParentResponseDto.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .phoneNo(saved.getPhoneNo())
                .companyName(saved.getCompanyName())
                .build();
    }

    public LogInResponseDto loginParentCompany(LogInRequestDto request) {
        // Check if parentCompany exists by email
        ParentCompany parentCompany = parentCompanyRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(request.getEmail()));

        // Check if password matches
        if (!passwordEncoder.matches(request.getPassword(), parentCompany.getPassword())) {
            throw new IncorrectPasswordException(request.getEmail());
        }

        // Generate access token (short-lived)
        String accessToken = jwtService.generateToken(parentCompany.getEmail(), Role.COMPANY_ADMIN);

        // Generate refresh token (long-lived)
        String refreshToken = UUID.randomUUID().toString();

        // Save refresh token in DB
        ParentRefreshToken parentRefreshToken = com.rahat.health_tracker.entity.diagnostic_center.ParentRefreshToken.builder()
                .parentCompany(parentCompany)
                .refreshToken(refreshToken)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiry))
                .build();

        parentRefreshTokenRepository.save(parentRefreshToken);

        // Return response
        return LogInResponseDto.builder()
                .token(accessToken)
                .tokenType("Bearer")
                .expiresIn(accessTokenExpiry)
                .refreshToken(refreshToken)
                .refreshExpiresIn(refreshTokenExpiry)
                .build();
    }



    @Transactional
    public LogInResponseDto refreshAccessToken(String refreshToken) {
        // Find the existing refresh token
        ParentRefreshToken token = parentRefreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        ParentCompany parentCompany = token.getParentCompany();

        // Generate new access token
        String newAccessToken = jwtService.generateToken(parentCompany.getEmail(), Role.COMPANY_ADMIN);

        // Generate new refresh token
        String newRefreshToken = UUID.randomUUID().toString();
        LocalDateTime newRefreshExpiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiry);

        // Save new refresh token and delete old one
        ParentRefreshToken parentRefreshToken = new ParentRefreshToken();
        parentRefreshToken.setParentCompany(parentCompany);
        parentRefreshToken.setRefreshToken(newRefreshToken);
        parentRefreshToken.setExpiresAt(newRefreshExpiresAt);
        parentRefreshTokenRepository.save(parentRefreshToken);

        // Delete old refresh token
        parentRefreshTokenRepository.delete(token);

        // Return updated tokens to client
        return LogInResponseDto.builder()
                .token(newAccessToken)
                .tokenType("Bearer")
                .expiresIn(accessTokenExpiry)
                .refreshToken(newRefreshToken)
                .refreshExpiresIn(refreshTokenExpiry)
                .build();
    }
}
