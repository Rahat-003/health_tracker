package com.rahat.health_tracker.dto.request;

import com.rahat.health_tracker.entity.doctor.master.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProfileRequestDto {
    private Long departmentId;
    private Long designationId;
    private Set<Long> instituteIds;
    private Set<Long> specialtyIds;
    private Set<Long> qualificationIds;
}
