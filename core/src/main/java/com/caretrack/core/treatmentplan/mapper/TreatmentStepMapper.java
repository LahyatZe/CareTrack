package com.caretrack.core.treatmentplan.mapper;

import com.caretrack.core.treatmentplan.domain.TreatmentStep;
import com.caretrack.core.treatmentplan.dto.TreatmentStepDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TreatmentStepMapper {

    @Mapping(target = "planId", source = "plan.id")
    TreatmentStepDto toDto(TreatmentStep step);

    @Mapping(target = "plan", ignore = true)
    TreatmentStep toEntity(TreatmentStepDto dto);
}
