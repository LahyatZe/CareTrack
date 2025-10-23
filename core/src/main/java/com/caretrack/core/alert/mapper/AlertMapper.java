package com.caretrack.core.alert.mapper;

import com.caretrack.core.alert.domain.Alert;
import com.caretrack.core.alert.domain.AlertStatus;
import com.caretrack.core.alert.dto.AlertDto;
import com.caretrack.core.common.mapper.EnumMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = EnumMapper.class)
public interface AlertMapper {

    @Mapping(target = "patientId", source = "patient.id")
    AlertDto toDto(Alert alert);

    @Mapping(target = "patient", ignore = true)
    Alert toEntity(AlertDto dto);

    @AfterMapping
    default void applyDefaults(@MappingTarget Alert alert) {
        if (alert.getStatus() == null) {
            alert.setStatus(AlertStatus.OPEN);
        }
    }
}
