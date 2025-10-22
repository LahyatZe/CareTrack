package com.caretrack.core.service;

import com.caretrack.core.domain.dto.PatientDto;
import java.util.List;
import java.util.Optional;

public interface PatientService {

    PatientDto create(PatientDto patientDto);

    Optional<PatientDto> findById(Long id);

    List<PatientDto> findAll();
}
