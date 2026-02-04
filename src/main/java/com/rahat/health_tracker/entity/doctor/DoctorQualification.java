package com.rahat.health_tracker.entity.doctor;

import com.rahat.health_tracker.entity.doctor.master.Doctor;
import com.rahat.health_tracker.entity.doctor.master.Institute;
import com.rahat.health_tracker.entity.doctor.master.Qualification;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(
    name = "doctor_qualification",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {
            "doctor_id",
            "qualification_id",
            "institute_id"
        }
    )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorQualification {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id", nullable = false)
    private Qualification qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", nullable = false)
    private Institute institute;
}

