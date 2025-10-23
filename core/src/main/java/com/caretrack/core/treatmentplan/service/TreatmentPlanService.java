package com.caretrack.core.treatmentplan.service;

import com.caretrack.core.treatmentplan.dto.TreatmentPlanDto;
import com.caretrack.core.treatmentplan.dto.TreatmentStepDto;
import java.util.List;
import java.util.Optional;

public interface TreatmentPlanService {

    TreatmentPlanDto create(TreatmentPlanDto dto);

    Optional<TreatmentPlanDto> findById(Long id);

    List<TreatmentPlanDto> findAll();

    TreatmentPlanDto update(Long id, TreatmentPlanDto dto);

    void delete(Long id);

    TreatmentStepDto addStep(Long planId, TreatmentStepDto stepDto);

    List<TreatmentPlanDto> findByPatient(Long patientId);
}
