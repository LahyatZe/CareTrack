package com.caretrack.persistence.repository;

import com.caretrack.core.medication.domain.MedicationIntake;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationIntakeRepository extends JpaRepository<MedicationIntake, Long> {

    List<MedicationIntake> findByPatientId(Long patientId);

    List<MedicationIntake> findByPlanId(Long planId);

    List<MedicationIntake> findByPatientIdAndPlanId(Long patientId, Long planId);
}
