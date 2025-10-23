package com.caretrack.core.patient.service;

import com.caretrack.core.patient.dto.PatientDto;
import com.caretrack.core.treatmentplan.dto.TreatmentPlanDto;
import java.util.List;
import java.util.Optional;

public interface PatientService {

    PatientDto create(PatientDto patientDto);

    Optional<PatientDto> findById(Long id);

    List<PatientDto> findAll();

    PatientDto update(Long id, PatientDto patientDto);

    void delete(Long id);

    List<TreatmentPlanDto> findPlans(Long patientId);
}
