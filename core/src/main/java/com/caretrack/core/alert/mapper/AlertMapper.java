package com.caretrack.core.alert.mapper;

import com.caretrack.core.alert.domain.Alert;
import com.caretrack.core.alert.dto.AlertDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlertMapper {

    @Mapping(target = "patientId", source = "patient.id")
    AlertDto toDto(Alert alert);

    @Mapping(target = "patient", ignore = true)
    Alert toEntity(AlertDto dto);
}
