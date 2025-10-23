package com.caretrack.core.user.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserCreateDto {
    @NotBlank
    String username;
    @NotBlank
    String password;
    String fullName;
    Set<String> roles;
}
