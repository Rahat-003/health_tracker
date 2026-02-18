package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.diagnostic_center.Branch;
import com.rahat.health_tracker.entity.diagnostic_center.ParentCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BranchRepository extends JpaRepository<Branch, Long> {
    boolean existsByEmail(String email);

    Optional<Branch> findByEmail(String email);

    Optional<Branch> findByEmailAndIsEnabled(String email, Boolean isEnabled);

}
