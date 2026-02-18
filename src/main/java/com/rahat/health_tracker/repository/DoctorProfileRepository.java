package com.rahat.health_tracker.repository;


import com.rahat.health_tracker.entity.doctor.DoctorProfile;
import com.rahat.health_tracker.entity.doctor.master.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    Optional<DoctorProfile> findByDoctor(Doctor doctor);

}
