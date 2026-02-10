package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.diagnostic_center.BranchRefreshToken;
import com.rahat.health_tracker.entity.diagnostic_center.ParentRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface BranchRefreshTokenRepository extends JpaRepository<BranchRefreshToken, Long> {
//    Optional<UserRefreshToken> findByR(String refreshToken);
    Optional<BranchRefreshToken> findByRefreshToken(String refreshToken);
}