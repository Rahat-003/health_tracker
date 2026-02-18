package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.doctor.master.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepository extends JpaRepository<Designation, Long> {
}
