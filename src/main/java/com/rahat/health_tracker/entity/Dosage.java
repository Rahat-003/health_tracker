package com.rahat.health_tracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "dosage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dosage {

    @Id
    @SequenceGenerator(name = "dosage_id_sequence", sequenceName = "dosage_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dosage_id_sequence")
    @Column(name = "dosage_id")
    private Long dosageId;


    @Column(nullable = false, length = 50)
    private String amount;       // 500mg, 10ml

    @Column(nullable = false, length = 50)
    private String frequency;

    @Column(length = 500)
    private String instruction;  // after meal, before sleep
}
