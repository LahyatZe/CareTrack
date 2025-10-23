package com.caretrack.core.vital.service;

import com.caretrack.core.vital.dto.VitalSignDto;
import java.util.List;
import java.util.Optional;

public interface VitalSignService {

    VitalSignDto create(VitalSignDto dto);

    Optional<VitalSignDto> findById(Long id);

    List<VitalSignDto> findByPatient(Long patientId);

    void delete(Long id);
}
