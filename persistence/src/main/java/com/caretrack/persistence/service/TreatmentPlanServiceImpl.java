package com.caretrack.persistence.service;

import com.caretrack.core.patient.domain.Patient;
import com.caretrack.core.treatmentplan.domain.TreatmentPlan;
import com.caretrack.core.treatmentplan.domain.TreatmentStep;
import com.caretrack.core.treatmentplan.dto.TreatmentPlanDto;
import com.caretrack.core.treatmentplan.dto.TreatmentStepDto;
import com.caretrack.core.treatmentplan.mapper.TreatmentPlanMapper;
import com.caretrack.core.treatmentplan.mapper.TreatmentStepMapper;
import com.caretrack.core.treatmentplan.service.TreatmentPlanService;
import com.caretrack.persistence.repository.PatientRepository;
import com.caretrack.persistence.repository.TreatmentPlanRepository;
import com.caretrack.persistence.repository.TreatmentStepRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TreatmentPlanServiceImpl implements TreatmentPlanService {

    private final TreatmentPlanRepository treatmentPlanRepository;
    private final TreatmentStepRepository treatmentStepRepository;
    private final PatientRepository patientRepository;
    private final TreatmentPlanMapper treatmentPlanMapper;
    private final TreatmentStepMapper treatmentStepMapper;

    @Override
    @Transactional
    public TreatmentPlanDto create(TreatmentPlanDto dto) {
        TreatmentPlan plan = treatmentPlanMapper.toEntity(dto);
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + dto.getPatientId()));
        plan.setPatient(patient);
        if (plan.getSteps() != null && !plan.getSteps().isEmpty()) {
            int index = 1;
            for (TreatmentStep step : plan.getSteps()) {
                if (step.getOrderIndex() == null) {
                    step.setOrderIndex(index);
                }
                index++;
            }
        }
        TreatmentPlan saved = treatmentPlanRepository.save(plan);
        return treatmentPlanMapper.toDto(saved);
    }

    @Override
    public Optional<TreatmentPlanDto> findById(Long id) {
        return treatmentPlanRepository.findWithStepsById(id).map(treatmentPlanMapper::toDto);
    }

    @Override
    public List<TreatmentPlanDto> findAll() {
        return treatmentPlanMapper.toDtoList(treatmentPlanRepository.findAll());
    }

    @Override
    @Transactional
    public TreatmentPlanDto update(Long id, TreatmentPlanDto dto) {
        TreatmentPlan plan = treatmentPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Treatment plan not found: " + id));
        if (dto.getPatientId() != null && !dto.getPatientId().equals(plan.getPatient().getId())) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + dto.getPatientId()));
            plan.setPatient(patient);
        }
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setStartDate(dto.getStartDate());
        plan.setEndDate(dto.getEndDate());
        TreatmentPlan saved = treatmentPlanRepository.save(plan);
        return treatmentPlanMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!treatmentPlanRepository.existsById(id)) {
            throw new EntityNotFoundException("Treatment plan not found: " + id);
        }
        treatmentPlanRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TreatmentStepDto addStep(Long planId, TreatmentStepDto stepDto) {
        TreatmentPlan plan = treatmentPlanRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Treatment plan not found: " + planId));
        TreatmentStep step = treatmentStepMapper.toEntity(stepDto);
        step.setPlan(plan);
        if (step.getOrderIndex() == null) {
            step.setOrderIndex(plan.getSteps().size() + 1);
        }
        TreatmentStep saved = treatmentStepRepository.save(step);
        return treatmentStepMapper.toDto(saved);
    }

    @Override
    public List<TreatmentPlanDto> findByPatient(Long patientId) {
        return treatmentPlanMapper.toDtoList(treatmentPlanRepository.findByPatientIdWithSteps(patientId));
    }
}
