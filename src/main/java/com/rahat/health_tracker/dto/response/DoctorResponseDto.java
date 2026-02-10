package com.rahat.health_tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DoctorResponseDto {
    private String id;
    private String email;
    private String name;
}
