package com.rahat.health_tracker.service;

import com.rahat.health_tracker.dto.request.ApplyChamberRequestDto;
import com.rahat.health_tracker.dto.request.DoctorProfileRequestDto;
import com.rahat.health_tracker.dto.response.DoctorInfo;
import com.rahat.health_tracker.dto.response.DoctorProfileResponseDto;
import com.rahat.health_tracker.entity.doctor.DoctorProfile;
import jakarta.validation.Valid;


public interface DoctorProfileService {
    DoctorInfo getInfo();

    DoctorProfile updateProfile(DoctorProfileRequestDto doctorProfileRequestDto);

    DoctorProfileResponseDto getDoctorProfile();

    Boolean applyChamber(@Valid ApplyChamberRequestDto request);

    DoctorProfileResponseDto createProfile(DoctorProfileRequestDto doctorProfileRequestDto);
}
