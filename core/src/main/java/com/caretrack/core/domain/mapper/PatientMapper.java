package com.caretrack.core.domain.mapper;

import com.caretrack.core.domain.dto.PatientDto;
import com.caretrack.core.domain.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {

    PatientDto toDto(Patient patient);

    Patient toEntity(PatientDto dto);
}
