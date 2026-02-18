package com.rahat.health_tracker.dto.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorProfileResponseDto {
//    private String doctorId;
    private String doctorName;
    private String designation;
    private String department;
    private List<String> institutes;
    private List<String> specialties;
    private List<String> qualifications;
    private List<ChamberResponseDto> chambers;
}
