package com.rahat.health_tracker.entity.diagnostic_center;


import com.rahat.health_tracker.entity.doctor.DoctorProfile;
import com.rahat.health_tracker.enums.DoctorOffDay;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chamber")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chamber {

    @Id
    @SequenceGenerator(name = "chamber_id_sequence", sequenceName = "chamber_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chamber_id_sequence")
    @Column(name = "chamber_id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "room_no", nullable = false)
    private Room roomNo;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; // 07:00 PM

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;   // 09:00 PM

    @Enumerated(EnumType.STRING)
    @Column(name = "off_day", nullable = false, length = 20)
    private DoctorOffDay offDay; // Use Enum

    @OneToMany(mappedBy = "chamber")
    private List<DoctorProfile> doctorProfile;

    private LocalDateTime createdAt;


}
