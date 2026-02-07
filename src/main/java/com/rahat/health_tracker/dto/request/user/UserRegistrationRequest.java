package com.rahat.health_tracker.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4, max = 20, message = "password must be between 6-20 characters")
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

}
