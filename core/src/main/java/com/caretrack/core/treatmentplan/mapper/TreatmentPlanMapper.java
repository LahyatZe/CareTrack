package com.caretrack.core.treatmentplan.mapper;

import com.caretrack.core.treatmentplan.domain.TreatmentPlan;
import com.caretrack.core.treatmentplan.dto.TreatmentPlanDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TreatmentStepMapper.class)
public interface TreatmentPlanMapper {

    @Mapping(target = "patientId", source = "patient.id")
    TreatmentPlanDto toDto(TreatmentPlan plan);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "steps", ignore = true)
    TreatmentPlan toEntity(TreatmentPlanDto dto);

    List<TreatmentPlanDto> toDtoList(List<TreatmentPlan> plans);
}
