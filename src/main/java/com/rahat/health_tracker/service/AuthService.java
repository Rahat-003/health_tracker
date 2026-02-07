package com.rahat.health_tracker.service;

import com.rahat.health_tracker.config.JwtService;
import com.rahat.health_tracker.dto.request.user.LogInRequestDto;
import com.rahat.health_tracker.dto.request.user.UserRegistrationRequest;
import com.rahat.health_tracker.dto.response.LogInResponseDto;
import com.rahat.health_tracker.dto.response.UserResponseDto;
import com.rahat.health_tracker.entity.User;
import com.rahat.health_tracker.entity.UserRefreshToken;
import com.rahat.health_tracker.exception.auth.EmailAlreadyExistsException;
import com.rahat.health_tracker.exception.auth.EmailNotFoundException;
import com.rahat.health_tracker.exception.auth.IncorrectPasswordException;
import com.rahat.health_tracker.repository.UserRefreshTokenRepository;
import com.rahat.health_tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private static final Long accessTokenExpiry = 15 * 60L; // 15 minutes in seconds
    private static final Long refreshTokenExpiry = 7 * 24 * 3600L; // 7 days in seconds

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UserRefreshTokenRepository userRefreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRefreshTokenRepository = userRefreshTokenRepository;
    }

    public UserResponseDto registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            System.out.println("email exists: " + request.getEmail());
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .id(savedUser.getUserId())
                .email(savedUser.getEmail())
                .build();
    }

    public LogInResponseDto loginUser(LogInRequestDto request) {
        // Check if user exists by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(request.getEmail()));

        // Check if password matches
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException(request.getEmail());
        }

        // Generate access token (short-lived)
        String accessToken = jwtService.generateToken(user.getEmail());

        // Generate refresh token (long-lived)
        String refreshToken = UUID.randomUUID().toString();

        // Save refresh token in DB
        UserRefreshToken userRefreshToken = UserRefreshToken.builder()
                .user(user)
                .refreshToken(refreshToken)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiry))
                .build();

        userRefreshTokenRepository.save(userRefreshToken);

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
        UserRefreshToken token = userRefreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        User user = token.getUser();

        // Generate new access token
        String newAccessToken = jwtService.generateToken(user.getEmail());

        // Generate new refresh token
        String newRefreshToken = UUID.randomUUID().toString();
        LocalDateTime newRefreshExpiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiry);

        // Save new refresh token and delete old one
        UserRefreshToken newTokenEntity = new UserRefreshToken();
        newTokenEntity.setUser(user);
        newTokenEntity.setRefreshToken(newRefreshToken);
        newTokenEntity.setExpiresAt(newRefreshExpiresAt);
        userRefreshTokenRepository.save(newTokenEntity);

        // Delete old refresh token
        userRefreshTokenRepository.delete(token);

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
