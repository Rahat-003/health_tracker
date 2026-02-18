package com.rahat.health_tracker.entity.doctor.master;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @SequenceGenerator(name = "department_id_sequence", sequenceName = "department_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_sequence")
    @Column(name = "department_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 200)
    private String name;
    // Orthopaedics Specialist & Surgeon
}
