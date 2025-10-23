package com.caretrack.core.medication.dto;

import com.caretrack.core.medication.domain.MedicationIntakeStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class MedicationIntakeDto {
    Long id;
    Long patientId;
    Long planId;
    String medicationName;
    String dosage;
    LocalDateTime scheduledTime;
    LocalDateTime takenAt;
    MedicationIntakeStatus status;
}
