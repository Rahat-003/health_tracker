package com.rahat.health_tracker.entity.doctor.master;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @SequenceGenerator(name = "department_id_sequence", sequenceName = "department_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_sequence")
    @Column(name = "department_id")
    private Long Id;

    @Column(nullable = false, unique = true, length = 200)
    private String name;
    // Orthopaedics Specialist & Surgeon
}
