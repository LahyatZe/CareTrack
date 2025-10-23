package com.caretrack.persistence.service;

import com.caretrack.core.alert.domain.Alert;
import com.caretrack.core.alert.domain.AlertStatus;
import com.caretrack.core.alert.dto.AlertDto;
import com.caretrack.core.alert.mapper.AlertMapper;
import com.caretrack.core.alert.service.AlertService;
import com.caretrack.core.patient.domain.Patient;
import com.caretrack.persistence.repository.AlertRepository;
import com.caretrack.persistence.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final PatientRepository patientRepository;
    private final AlertMapper alertMapper;

    @Override
    @Transactional
    public AlertDto create(AlertDto dto) {
        Alert alert = alertMapper.toEntity(dto);
        if (dto.getPatientId() != null) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + dto.getPatientId()));
            alert.setPatient(patient);
        }
        if (alert.getCreatedAt() == null) {
            alert.setCreatedAt(LocalDateTime.now());
        }
        Alert saved = alertRepository.save(alert);
        return alertMapper.toDto(saved);
    }

    @Override
    public List<AlertDto> findByFilters(AlertStatus status, Long patientId) {
        List<Alert> alerts;
        if (status != null && patientId != null) {
            alerts = alertRepository.findByStatusAndPatientId(status, patientId);
        } else if (status != null) {
            alerts = alertRepository.findByStatus(status);
        } else if (patientId != null) {
            alerts = alertRepository.findByPatientId(patientId);
        } else {
            alerts = alertRepository.findAll();
        }
        return alerts.stream().map(alertMapper::toDto).toList();
    }

    @Override
    @Transactional
    public AlertDto acknowledge(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert not found: " + id));
        if (alert.getStatus() == AlertStatus.OPEN) {
            alert.setStatus(AlertStatus.ACKNOWLEDGED);
            alert.setAcknowledgedAt(LocalDateTime.now());
        }
        Alert saved = alertRepository.save(alert);
        return alertMapper.toDto(saved);
    }

    @Override
    @Transactional
    public AlertDto resolve(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert not found: " + id));
        alert.setStatus(AlertStatus.RESOLVED);
        if (alert.getAcknowledgedAt() == null) {
            alert.setAcknowledgedAt(LocalDateTime.now());
        }
        alert.setResolvedAt(LocalDateTime.now());
        Alert saved = alertRepository.save(alert);
        return alertMapper.toDto(saved);
    }
}
