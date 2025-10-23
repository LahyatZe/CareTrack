package com.caretrack.persistence.repository;

import com.caretrack.core.treatmentplan.domain.TreatmentStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentStepRepository extends JpaRepository<TreatmentStep, Long> {
}
