package com.rahat.health_tracker.entity.diagnostic_center;

import com.rahat.health_tracker.entity.doctor.DoctorProfile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {


    @Id
    @SequenceGenerator(name = "room_id_sequence", sequenceName = "room_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_sequence")
    @Column(name = "room_id")
    private Long Id;

    private String roomNo;

    @Column(name = "floor_no")
    private Long floorNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", nullable = false, unique = true)
    private Branch branch;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

