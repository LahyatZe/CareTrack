package com.caretrack.core.vital.mapper;

import com.caretrack.core.vital.domain.VitalSign;
import com.caretrack.core.vital.dto.VitalSignDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VitalSignMapper {

    @Mapping(target = "patientId", source = "patient.id")
    VitalSignDto toDto(VitalSign vitalSign);

    @Mapping(target = "patient", ignore = true)
    VitalSign toEntity(VitalSignDto dto);
}
