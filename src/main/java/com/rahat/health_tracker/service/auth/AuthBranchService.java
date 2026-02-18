package com.rahat.health_tracker.service.auth;

import com.rahat.health_tracker.enums.Role;
import com.rahat.health_tracker.security.JwtService;
import com.rahat.health_tracker.dto.request.auth.BranchRegistrationRequest;
import com.rahat.health_tracker.dto.request.auth.LogInRequestDto;
import com.rahat.health_tracker.dto.response.BranchResponseDto;
import com.rahat.health_tracker.dto.response.LogInResponseDto;
import com.rahat.health_tracker.entity.diagnostic_center.Branch;
import com.rahat.health_tracker.entity.diagnostic_center.BranchRefreshToken;
import com.rahat.health_tracker.entity.diagnostic_center.ParentCompany;
import com.rahat.health_tracker.exception.auth.EmailAlreadyExistsException;
import com.rahat.health_tracker.exception.auth.EmailNotFoundException;
import com.rahat.health_tracker.exception.auth.IncorrectPasswordException;
import com.rahat.health_tracker.exception.auth.ParentCompanyNotFoundException;
import com.rahat.health_tracker.repository.BranchRefreshTokenRepository;
import com.rahat.health_tracker.repository.BranchRepository;
import com.rahat.health_tracker.repository.ParentCompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthBranchService {
    private final BranchRepository branchRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final BranchRefreshTokenRepository branchRefreshTokenRepository;
    private final ParentCompanyRepository parentCompanyRepository;


    @Value("${jwt.access-token-expiry}")
    private Long accessTokenExpiry;

    @Value("${jwt.refresh-token-expiry}")
    private Long refreshTokenExpiry;

    public BranchResponseDto registerBranch(BranchRegistrationRequest request) {
        if (branchRepository.existsByEmail(request.getEmail())) {
//            System.out.println("email exists: " + request.getEmail());
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        ParentCompany parentCompany = parentCompanyRepository.findById(request.getParentCompanyId())
                .orElseThrow(()-> new ParentCompanyNotFoundException(request.getParentCompanyId()));

        Branch branch = Branch.builder()
                . branchName(request.getBranchName())
                .parentCompany(parentCompany)
                .email(request.getEmail())
                .phoneNo(request.getPhoneNo())
                .password(passwordEncoder.encode(request.getPassword()))
                .isEnabled(true)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .branchAddress(request.getBranchAddress())
                .role(Role.BRANCH_ADMIN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Branch savedBranch = branchRepository.save(branch);

        return BranchResponseDto.builder()
                .id(savedBranch.getId())
                .branchName(savedBranch.getBranchName())
                .email(savedBranch.getEmail())
                .phoneNo(savedBranch.getPhoneNo())
                .latitude(savedBranch.getLatitude())
                .longitude(savedBranch.getLongitude())
                .branchAddress(savedBranch.getBranchAddress())
                .build();
    }

    public LogInResponseDto loginBranch(LogInRequestDto request) {
        // Check if branch exists by email
        Branch branch = branchRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(request.getEmail()));

        // Check if password matches
        if (!passwordEncoder.matches(request.getPassword(), branch.getPassword())) {
            throw new IncorrectPasswordException(request.getEmail());
        }

        // Generate access token (short-lived)
        String accessToken = jwtService.generateToken(branch.getEmail(), Role.BRANCH_ADMIN);

        // Generate refresh token (long-lived)
        String refreshToken = UUID.randomUUID().toString();

        // Save refresh token in DB
        BranchRefreshToken branchRefreshToken = BranchRefreshToken.builder()
                .refreshToken(refreshToken)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiry))
                .build();

        branchRefreshTokenRepository.save(branchRefreshToken);

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
        BranchRefreshToken token = branchRefreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        Branch branch = token.getBranch();

        // Generate new access token
        String newAccessToken = jwtService.generateToken(branch.getEmail(), Role.BRANCH_ADMIN);

        // Generate new refresh token
        String newRefreshToken = UUID.randomUUID().toString();
        LocalDateTime newRefreshExpiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiry);

        // Save new refresh token and delete old one
        BranchRefreshToken branchRefreshToken = new BranchRefreshToken();
        branchRefreshToken.setBranch(branch);
        branchRefreshToken.setRefreshToken(newRefreshToken);
        branchRefreshToken.setExpiresAt(newRefreshExpiresAt);
        branchRefreshTokenRepository.save(branchRefreshToken);

        // Delete old refresh token
        branchRefreshTokenRepository.delete(token);

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
