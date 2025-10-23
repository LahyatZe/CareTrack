package com.caretrack.core.treatmentplan.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class TreatmentPlanDto {
    Long id;
    Long patientId;
    String name;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    @Singular
    List<TreatmentStepDto> steps;
}
