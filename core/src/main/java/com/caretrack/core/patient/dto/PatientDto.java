package com.caretrack.core.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PatientDto {
    Long id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Email
    String email;
}
