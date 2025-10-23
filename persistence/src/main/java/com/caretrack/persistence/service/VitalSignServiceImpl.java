package com.caretrack.persistence.service;

import com.caretrack.core.patient.domain.Patient;
import com.caretrack.core.vital.domain.VitalSign;
import com.caretrack.core.vital.dto.VitalSignDto;
import com.caretrack.core.vital.mapper.VitalSignMapper;
import com.caretrack.core.vital.service.VitalSignService;
import com.caretrack.persistence.repository.PatientRepository;
import com.caretrack.persistence.repository.VitalSignRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VitalSignServiceImpl implements VitalSignService {

    private final VitalSignRepository vitalSignRepository;
    private final PatientRepository patientRepository;
    private final VitalSignMapper vitalSignMapper;

    @Override
    @Transactional
    public VitalSignDto create(VitalSignDto dto) {
        VitalSign vitalSign = vitalSignMapper.toEntity(dto);
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + dto.getPatientId()));
        vitalSign.setPatient(patient);
        if (vitalSign.getRecordedAt() == null) {
            vitalSign.setRecordedAt(LocalDateTime.now());
        }
        VitalSign saved = vitalSignRepository.save(vitalSign);
        return vitalSignMapper.toDto(saved);
    }

    @Override
    public Optional<VitalSignDto> findById(Long id) {
        return vitalSignRepository.findById(id).map(vitalSignMapper::toDto);
    }

    @Override
    public List<VitalSignDto> findByPatient(Long patientId) {
        return vitalSignRepository.findByPatientId(patientId).stream().map(vitalSignMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!vitalSignRepository.existsById(id)) {
            throw new EntityNotFoundException("Vital sign not found: " + id);
        }
        vitalSignRepository.deleteById(id);
    }
}
