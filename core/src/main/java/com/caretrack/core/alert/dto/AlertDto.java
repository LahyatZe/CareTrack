package com.caretrack.core.alert.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AlertDto {
    Long id;
    Long patientId;
    String message;
    String status;
    LocalDateTime createdAt;
    LocalDateTime acknowledgedAt;
    LocalDateTime resolvedAt;
}
