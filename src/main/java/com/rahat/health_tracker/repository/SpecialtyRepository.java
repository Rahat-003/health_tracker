package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.doctor.master.Designation;
import com.rahat.health_tracker.entity.doctor.master.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
}
