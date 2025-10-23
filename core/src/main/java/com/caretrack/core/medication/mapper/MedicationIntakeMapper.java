package com.caretrack.core.medication.mapper;

import com.caretrack.core.common.mapper.EnumMapper;
import com.caretrack.core.medication.domain.MedicationIntake;
import com.caretrack.core.medication.domain.MedicationIntakeStatus;
import com.caretrack.core.medication.dto.MedicationIntakeDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = EnumMapper.class)
public interface MedicationIntakeMapper {

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "planId", source = "plan.id")
    MedicationIntakeDto toDto(MedicationIntake intake);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "plan", ignore = true)
    MedicationIntake toEntity(MedicationIntakeDto dto);

    @AfterMapping
    default void applyDefaults(@MappingTarget MedicationIntake intake) {
        if (intake.getStatus() == null) {
            intake.setStatus(MedicationIntakeStatus.SCHEDULED);
        }
    }
}
