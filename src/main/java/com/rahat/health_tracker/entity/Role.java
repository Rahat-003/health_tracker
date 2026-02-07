package com.rahat.health_tracker.entity;


import com.rahat.health_tracker.enums.Role_Enum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment Long
    @Column(name = "role_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role_Enum role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}

