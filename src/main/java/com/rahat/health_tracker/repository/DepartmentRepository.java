package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.doctor.master.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
