package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.doctor.master.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DoctorRepository extends JpaRepository<Doctor, String> {
    boolean existsByEmail(String email);

    Optional<Doctor> findByEmail(String email);

}
