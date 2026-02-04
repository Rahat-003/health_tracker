package com.rahat.health_tracker.entity.doctor;

import com.rahat.health_tracker.entity.doctor.master.Doctor;
import com.rahat.health_tracker.entity.doctor.master.Specialty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;


@Entity
@Table(
    name = "doctor_specialty",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {
            "doctor_id",
            "specialty_id"
        }
    )
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorSpecialty {
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;
}
