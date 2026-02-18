package com.rahat.health_tracker.dto.request;

import com.rahat.health_tracker.enums.DoctorOffDay;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyChamberRequestDto {

    @NotNull(message = "Branch id is required")
    private Long branchId;

    @NotNull(message = "Visiting fee is required")
    @Positive(message = "Visiting fee must be greater than 0")
    private Integer visitingFee;

    private List<DoctorOffDay> offDayList;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @AssertTrue(message = "End time must be after start time")
    public boolean isValidTimeRange() {
        if (startTime == null || endTime == null) return true;
        return endTime.isAfter(startTime);
    }
}
