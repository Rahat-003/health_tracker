package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.doctor.master.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
}
