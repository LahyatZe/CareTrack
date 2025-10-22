package com.caretrack.persistence.service;

import com.caretrack.core.domain.dto.PatientDto;
import com.caretrack.core.domain.entity.Patient;
import com.caretrack.core.domain.mapper.PatientMapper;
import com.caretrack.core.service.PatientService;
import com.caretrack.persistence.repository.PatientRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientDto create(PatientDto patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);
        Patient persisted = patientRepository.save(patient);
        return patientMapper.toDto(persisted);
    }

    @Override
    public Optional<PatientDto> findById(Long id) {
        return patientRepository.findById(id).map(patientMapper::toDto);
    }

    @Override
    public List<PatientDto> findAll() {
        return patientRepository.findAll().stream().map(patientMapper::toDto).toList();
    }
}
