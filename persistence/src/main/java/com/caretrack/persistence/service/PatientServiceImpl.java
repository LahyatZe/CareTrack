package com.caretrack.persistence.service;

import com.caretrack.core.patient.domain.Patient;
import com.caretrack.core.patient.dto.PatientDto;
import com.caretrack.core.patient.mapper.PatientMapper;
import com.caretrack.core.patient.service.PatientService;
import com.caretrack.core.treatmentplan.dto.TreatmentPlanDto;
import com.caretrack.core.treatmentplan.mapper.TreatmentPlanMapper;
import com.caretrack.persistence.repository.PatientRepository;
import com.caretrack.persistence.repository.TreatmentPlanRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final PatientMapper patientMapper;
    private final TreatmentPlanMapper treatmentPlanMapper;

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

    @Override
    @Transactional
    public PatientDto update(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setEmail(patientDto.getEmail());
        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found: " + id);
        }
        patientRepository.deleteById(id);
    }

    @Override
    public List<TreatmentPlanDto> findPlans(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException("Patient not found: " + patientId);
        }
        return treatmentPlanMapper.toDtoList(treatmentPlanRepository.findByPatientIdWithSteps(patientId));
    }
}
