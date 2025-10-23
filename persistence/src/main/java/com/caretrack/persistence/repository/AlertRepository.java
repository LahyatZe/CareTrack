package com.caretrack.persistence.repository;

import com.caretrack.core.alert.domain.Alert;
import com.caretrack.core.alert.domain.AlertStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByStatus(AlertStatus status);

    List<Alert> findByPatientId(Long patientId);

    List<Alert> findByStatusAndPatientId(AlertStatus status, Long patientId);
}
