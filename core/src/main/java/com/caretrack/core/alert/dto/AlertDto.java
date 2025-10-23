package com.caretrack.core.alert.dto;

import com.caretrack.core.alert.domain.AlertStatus;
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
    AlertStatus status;
    LocalDateTime createdAt;
    LocalDateTime acknowledgedAt;
    LocalDateTime resolvedAt;
}
