package com.rahat.health_tracker.service.auth;

import com.rahat.health_tracker.config.JwtService;
import com.rahat.health_tracker.dto.request.auth.DoctorRegistrationRequest;
import com.rahat.health_tracker.dto.request.auth.LogInRequestDto;
import com.rahat.health_tracker.dto.response.DoctorResponseDto;
import com.rahat.health_tracker.dto.response.LogInResponseDto;
import com.rahat.health_tracker.entity.User;
import com.rahat.health_tracker.entity.UserRefreshToken;
import com.rahat.health_tracker.entity.doctor.DoctorRefreshToken;
import com.rahat.health_tracker.entity.doctor.master.Doctor;
import com.rahat.health_tracker.exception.auth.EmailAlreadyExistsException;
import com.rahat.health_tracker.exception.auth.EmailNotFoundException;
import com.rahat.health_tracker.exception.auth.IncorrectPasswordException;
import com.rahat.health_tracker.repository.DoctorRefreshTokenRepository;
import com.rahat.health_tracker.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthDoctorService {
    private final DoctorRepository doctorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final DoctorRefreshTokenRepository doctorRefreshTokenRepository;

    private static final Long accessTokenExpiry = 15 * 60L; // 15 minutes in seconds
    private static final Long refreshTokenExpiry = 7 * 24 * 3600L; // 7 days in seconds


    public DoctorResponseDto registerUser(DoctorRegistrationRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        Doctor doctor = Doctor.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);

        return DoctorResponseDto.builder()
                .id(savedDoctor.getDoctorId())
                .email(savedDoctor.getEmail())
                .name(savedDoctor.getName())
                .build();
    }

    public LogInResponseDto loginUser(LogInRequestDto request) {
        // Check if doctor exists by email
        Doctor doctor = doctorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(request.getEmail()));

        // Check if password matches
        if (!passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
            throw new IncorrectPasswordException(request.getEmail());
        }

        // Generate access token (short-lived)
        String accessToken = jwtService.generateToken(doctor.getEmail());

        // Generate refresh token (long-lived)
        String refreshToken = UUID.randomUUID().toString();

        // Save refresh token in DB
        DoctorRefreshToken doctorRefreshToken = DoctorRefreshToken.builder()
                .doctor(doctor)
                .refreshToken(refreshToken)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiry))
                .build();

        doctorRefreshTokenRepository.save(doctorRefreshToken);

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
        DoctorRefreshToken token = doctorRefreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        Doctor doctor = token.getDoctor();

        // Generate new access token
        String newAccessToken = jwtService.generateToken(doctor.getEmail());

        // Generate new refresh token
        String newRefreshToken = UUID.randomUUID().toString();
        LocalDateTime newRefreshExpiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiry);

        // Save new refresh token and delete old one
        DoctorRefreshToken newTokenEntity = new DoctorRefreshToken();
        newTokenEntity.setDoctor(doctor);
        newTokenEntity.setRefreshToken(newRefreshToken);
        newTokenEntity.setExpiresAt(newRefreshExpiresAt);
        doctorRefreshTokenRepository.save(newTokenEntity);

        // Delete old refresh token
        doctorRefreshTokenRepository.delete(token);

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
