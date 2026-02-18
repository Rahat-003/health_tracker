package com.rahat.health_tracker.mapper;

import com.rahat.health_tracker.dto.response.DoctorInfo;
import com.rahat.health_tracker.entity.doctor.master.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DoctorInfoMapper {

    DoctorInfoMapper INSTANCE = Mappers.getMapper(DoctorInfoMapper.class);

    DoctorInfo toDoctorInfo(
            List<Department> departmentList,
            List<Designation> designationList,
            List<Institute> instituteList,
            List<Specialty> specialtyList,
            List<Qualification> qualificationList);
}
