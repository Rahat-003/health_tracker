package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.diagnostic_center.ParentCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ParentCompanyRepository extends JpaRepository<ParentCompany, Long> {
    boolean existsByEmail(String email);

    Optional<ParentCompany> findByEmail(String email);

}
