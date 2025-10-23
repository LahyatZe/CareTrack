package com.caretrack.persistence.repository;

import com.caretrack.core.treatmentplan.domain.TreatmentPlan;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {

    @EntityGraph(attributePaths = "steps")
    @Query("select tp from TreatmentPlan tp where tp.patient.id = :patientId")
    List<TreatmentPlan> findByPatientIdWithSteps(Long patientId);

    @EntityGraph(attributePaths = "steps")
    @Override
    List<TreatmentPlan> findAll();

    @EntityGraph(attributePaths = "steps")
    Optional<TreatmentPlan> findWithStepsById(Long id);
}
