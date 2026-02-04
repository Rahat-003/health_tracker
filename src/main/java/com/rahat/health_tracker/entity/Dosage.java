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
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME) // UUID v7
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(nullable = false, length = 50)
    private String amount;       // 500mg, 10ml

    @Column(nullable = false, length = 50)
    private String frequency;

    @Column(length = 500)
    private String instruction;  // after meal, before sleep
}
