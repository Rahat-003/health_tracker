package com.rahat.health_tracker.entity.diagnostic_center;


import com.rahat.health_tracker.entity.doctor.DoctorProfile;
import com.rahat.health_tracker.entity.doctor.master.Doctor;
import com.rahat.health_tracker.enums.DoctorOffDay;
import com.rahat.health_tracker.persistence.converter.DoctorOffDayConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "chamber",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"branch_id", "room_no", "floor_no"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chamber {

    @Id
    @SequenceGenerator(name = "chamber_id_sequence", sequenceName = "chamber_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chamber_id_sequence")
    @Column(name = "chamber_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "room_no", nullable = false)
    private String roomNo;

    @Column(name = "floor_no")
    private String floorNo;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; // 07:00 PM

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;   // 09:00 PM

    @Convert(converter = DoctorOffDayConverter.class)
    @Column(name = "doctor_off_day", length = 100)
    private EnumSet<DoctorOffDay> doctorOffDay;

    private Boolean isActive;

    private Integer visitingFee;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
