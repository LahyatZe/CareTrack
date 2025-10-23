package com.caretrack.core.common.mapper;

import com.caretrack.core.alert.domain.AlertStatus;
import com.caretrack.core.medication.domain.MedicationIntakeStatus;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class EnumMapper {

    public String asString(AlertStatus status) {
        return status != null ? status.name() : null;
    }

    public AlertStatus toAlertStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        try {
            return AlertStatus.valueOf(status.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown alert status: " + status);
        }
    }

    public String asString(MedicationIntakeStatus status) {
        return status != null ? status.name() : null;
    }

    public MedicationIntakeStatus toMedicationIntakeStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        try {
            return MedicationIntakeStatus.valueOf(status.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown medication intake status: " + status);
        }
    }
}
