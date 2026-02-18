package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.dto.response.ChamberResponseDto;
import com.rahat.health_tracker.entity.diagnostic_center.Branch;
import com.rahat.health_tracker.entity.diagnostic_center.Chamber;
import com.rahat.health_tracker.entity.doctor.master.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChamberRepository extends JpaRepository<Chamber, Long> {

//    @Query(
//            nativeQuery = true,
//            value = ""
//    )
//    List<ChamberResponseDto> getChamberListForDoctor(String doctorDoctorId);

    List<Chamber> findByDoctor_DoctorIdAndIsActive(String doctorId, boolean b);

    Optional<Chamber> findByDoctorAndBranch(Doctor doctor, Branch branch);
}
