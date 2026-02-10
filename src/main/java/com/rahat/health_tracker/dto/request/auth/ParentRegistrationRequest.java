package com.rahat.health_tracker.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ParentRegistrationRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 20, message = "password must be between 4-20 characters")
    private String password;

    @NotBlank
    private String companyName;

    @NotBlank
    private String phoneNo;
}
