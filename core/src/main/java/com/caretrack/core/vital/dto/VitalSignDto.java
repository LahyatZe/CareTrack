package com.caretrack.core.vital.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class VitalSignDto {
    Long id;
    Long patientId;
    String type;
    String value;
    String unit;
    LocalDateTime recordedAt;
}
