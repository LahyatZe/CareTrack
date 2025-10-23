package com.caretrack.persistence.service;

import com.caretrack.core.common.mapper.EnumMapper;
import com.caretrack.core.medication.domain.MedicationIntake;
import com.caretrack.core.medication.domain.MedicationIntakeStatus;
import com.caretrack.core.medication.dto.MedicationIntakeDto;
import com.caretrack.core.medication.mapper.MedicationIntakeMapper;
import com.caretrack.core.medication.service.MedicationIntakeService;
import com.caretrack.core.patient.domain.Patient;
import com.caretrack.core.treatmentplan.domain.TreatmentPlan;
import com.caretrack.persistence.repository.MedicationIntakeRepository;
import com.caretrack.persistence.repository.PatientRepository;
import com.caretrack.persistence.repository.TreatmentPlanRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicationIntakeServiceImpl implements MedicationIntakeService {

    private final MedicationIntakeRepository medicationIntakeRepository;
    private final PatientRepository patientRepository;
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final MedicationIntakeMapper medicationIntakeMapper;
    private final EnumMapper enumMapper;

    @Override
    @Transactional
    public MedicationIntakeDto create(MedicationIntakeDto dto) {
        MedicationIntake intake = medicationIntakeMapper.toEntity(dto);
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + dto.getPatientId()));
        intake.setPatient(patient);
        if (dto.getPlanId() != null) {
            TreatmentPlan plan = treatmentPlanRepository.findById(dto.getPlanId())
                    .orElseThrow(() -> new EntityNotFoundException("Treatment plan not found: " + dto.getPlanId()));
            intake.setPlan(plan);
        }
        MedicationIntake saved = medicationIntakeRepository.save(intake);
        return medicationIntakeMapper.toDto(saved);
    }

    @Override
    public List<MedicationIntakeDto> findByFilters(Long patientId, Long planId) {
        List<MedicationIntake> results;
        if (patientId != null && planId != null) {
            results = medicationIntakeRepository.findByPatientIdAndPlanId(patientId, planId);
        } else if (patientId != null) {
            results = medicationIntakeRepository.findByPatientId(patientId);
        } else if (planId != null) {
            results = medicationIntakeRepository.findByPlanId(planId);
        } else {
            results = medicationIntakeRepository.findAll();
        }
        return results.stream().map(medicationIntakeMapper::toDto).toList();
    }

    @Override
    @Transactional
    public MedicationIntakeDto updateStatus(Long id, String status) {
        MedicationIntake intake = medicationIntakeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medication intake not found: " + id));
        MedicationIntakeStatus targetStatus = enumMapper.toMedicationIntakeStatus(status);
        if (targetStatus == null) {
            throw new IllegalArgumentException("Status must be provided for update");
        }
        if (!Objects.equals(intake.getStatus(), targetStatus)) {
            intake.setStatus(targetStatus);
            if (targetStatus == MedicationIntakeStatus.TAKEN) {
                intake.setTakenAt(java.time.LocalDateTime.now());
            } else {
                intake.setTakenAt(null);
            }
        }
        MedicationIntake saved = medicationIntakeRepository.save(intake);
        return medicationIntakeMapper.toDto(saved);
    }
}
