package com.caretrack.core.alert.service;

import com.caretrack.core.alert.domain.AlertStatus;
import com.caretrack.core.alert.dto.AlertDto;
import java.util.List;

public interface AlertService {

    AlertDto create(AlertDto dto);

    List<AlertDto> findByFilters(AlertStatus status, Long patientId);

    AlertDto acknowledge(Long id);

    AlertDto resolve(Long id);
}
