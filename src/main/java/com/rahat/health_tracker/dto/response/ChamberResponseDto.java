package com.rahat.health_tracker.dto.response;

import lombok.*;

import java.time.LocalTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChamberResponseDto {

    private Long chamberId;

    // Branch-level
    private String branchName;
    private Double latitude;
    private Double longitude;
    private String branchAddress;

    // Room-level
    private String roomNo;
    private String floorNo;

    // Chamber-level
    private LocalTime startTime;
    private LocalTime endTime;
    private String offDays;
}
