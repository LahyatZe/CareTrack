package com.caretrack.core.treatmentplan.mapper;

import com.caretrack.core.treatmentplan.domain.TreatmentPlan;
import com.caretrack.core.treatmentplan.dto.TreatmentPlanDto;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TreatmentStepMapper.class)
public interface TreatmentPlanMapper {

    @Mapping(target = "patientId", source = "patient.id")
    TreatmentPlanDto toDto(TreatmentPlan plan);

    @Mapping(target = "patient", ignore = true)
    TreatmentPlan toEntity(TreatmentPlanDto dto);

    List<TreatmentPlanDto> toDtoList(List<TreatmentPlan> plans);

    @AfterMapping
    default void linkSteps(@MappingTarget TreatmentPlan plan) {
        if (plan.getSteps() != null) {
            plan.getSteps().forEach(step -> step.setPlan(plan));
        }
    }
}
