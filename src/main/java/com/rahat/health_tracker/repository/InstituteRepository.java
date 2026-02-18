package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.doctor.master.Department;
import com.rahat.health_tracker.entity.doctor.master.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
}
