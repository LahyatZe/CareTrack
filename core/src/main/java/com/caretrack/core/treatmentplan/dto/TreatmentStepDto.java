package com.caretrack.core.treatmentplan.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class TreatmentStepDto {
    Long id;
    Long planId;
    String title;
    String instructions;
    Integer orderIndex;
    LocalDate scheduledDate;
}
