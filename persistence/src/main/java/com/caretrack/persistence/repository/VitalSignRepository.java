package com.caretrack.persistence.repository;

import com.caretrack.core.vital.domain.VitalSign;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VitalSignRepository extends JpaRepository<VitalSign, Long> {

    List<VitalSign> findByPatientId(Long patientId);
}
