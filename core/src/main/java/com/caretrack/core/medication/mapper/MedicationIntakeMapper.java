package com.caretrack.core.medication.mapper;

import com.caretrack.core.medication.domain.MedicationIntake;
import com.caretrack.core.medication.dto.MedicationIntakeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicationIntakeMapper {

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "planId", source = "plan.id")
    MedicationIntakeDto toDto(MedicationIntake intake);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "plan", ignore = true)
    MedicationIntake toEntity(MedicationIntakeDto dto);
}
