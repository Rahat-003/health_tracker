package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.diagnostic_center.ParentRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface ParentRefreshTokenRepository extends JpaRepository<ParentRefreshToken, Long> {
//    Optional<UserRefreshToken> findByR(String refreshToken);
    Optional<ParentRefreshToken> findByRefreshToken(String refreshToken);
}