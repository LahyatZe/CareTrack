package com.caretrack.core.patient.mapper;

import com.caretrack.core.patient.dto.PatientDto;
import com.caretrack.core.patient.domain.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {

    PatientDto toDto(Patient patient);

    Patient toEntity(PatientDto dto);
}
