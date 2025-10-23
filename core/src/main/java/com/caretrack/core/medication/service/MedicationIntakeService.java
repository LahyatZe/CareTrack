package com.caretrack.core.medication.service;

import com.caretrack.core.medication.domain.MedicationIntakeStatus;
import com.caretrack.core.medication.dto.MedicationIntakeDto;
import java.util.List;

public interface MedicationIntakeService {

    MedicationIntakeDto create(MedicationIntakeDto dto);

    List<MedicationIntakeDto> findByFilters(Long patientId, Long planId);

    MedicationIntakeDto updateStatus(Long id, MedicationIntakeStatus status);
}
