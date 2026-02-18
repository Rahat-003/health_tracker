package com.rahat.health_tracker.dto.response;

import com.rahat.health_tracker.entity.doctor.master.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorInfo {
    private List<Department> departmentList;
    private List<Designation> designationList;
    private List<Institute> instituteList;
    private List<Specialty> specialtyList;
    private List<Qualification> qualificationList;
}
