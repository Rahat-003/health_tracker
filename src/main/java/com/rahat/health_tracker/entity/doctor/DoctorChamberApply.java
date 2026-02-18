package com.rahat.health_tracker.entity.doctor;


import com.rahat.health_tracker.entity.diagnostic_center.Branch;
import com.rahat.health_tracker.entity.doctor.master.Doctor;
import com.rahat.health_tracker.enums.DoctorOffDay;
import com.rahat.health_tracker.persistence.converter.DoctorOffDayConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

@Entity
@Table(name = "doctor_chamber_apply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorChamberApply {
    @Id
    @SequenceGenerator(name = "doctor_chamber_apply_id_sequence", sequenceName = "doctor_chamber_apply_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_chamber_apply_id_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branchId;

    @Convert(converter = DoctorOffDayConverter.class)
    @Column(name = "doctor_off_day", length = 100)
    private EnumSet<DoctorOffDay> doctorOffDay;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; // 07:00 PM

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;   // 09:00 PM

    private Boolean isActive;

    private Integer visitingFee;

    private LocalDateTime createdAt;

    private LocalDateTime acceptedAt;

    private Boolean isAccepted;
}
