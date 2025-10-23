package com.caretrack.api.patient;

import com.caretrack.core.patient.dto.PatientDto;
import com.caretrack.core.patient.service.PatientService;
import com.caretrack.core.treatmentplan.dto.TreatmentPlanDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Patient management endpoints")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @Operation(summary = "Create a patient")
    public ResponseEntity<PatientDto> create(@RequestBody @Validated PatientDto patientDto) {
        PatientDto created = patientService.create(patientDto);
        return ResponseEntity.created(URI.create("/api/patients/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a patient by id")
    public ResponseEntity<PatientDto> findById(@PathVariable Long id) {
        return patientService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List all patients")
    public ResponseEntity<List<PatientDto>> findAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a patient")
    public ResponseEntity<PatientDto> update(@PathVariable Long id, @RequestBody @Validated PatientDto patientDto) {
        return ResponseEntity.ok(patientService.update(id, patientDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/plans")
    @Operation(summary = "List treatment plans for a patient")
    public ResponseEntity<List<TreatmentPlanDto>> findPlans(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.findPlans(id));
    }
}
