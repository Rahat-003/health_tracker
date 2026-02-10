package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.diagnostic_center.ParentRefreshToken;
import com.rahat.health_tracker.entity.doctor.DoctorRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface DoctorRefreshTokenRepository extends JpaRepository<DoctorRefreshToken, Long> {
//    Optional<UserRefreshToken> findByR(String refreshToken);
    Optional<DoctorRefreshToken> findByRefreshToken(String refreshToken);
}