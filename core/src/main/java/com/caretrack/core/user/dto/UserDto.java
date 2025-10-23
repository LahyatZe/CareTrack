package com.caretrack.core.user.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserDto {
    Long id;
    String username;
    String fullName;
    Set<String> roles;
}
