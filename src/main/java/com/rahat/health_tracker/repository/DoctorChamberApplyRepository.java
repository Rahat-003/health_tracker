package com.rahat.health_tracker.repository;

import com.rahat.health_tracker.entity.diagnostic_center.Branch;
import com.rahat.health_tracker.entity.doctor.DoctorChamberApply;
import com.rahat.health_tracker.entity.doctor.master.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorChamberApplyRepository extends JpaRepository<DoctorChamberApply, Long> {
    DoctorChamberApply findByDoctorIdAndBranchIdAndIsAccepted(Doctor doctor, Branch branch, boolean b);
}
