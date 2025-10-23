package com.caretrack.api.medication;

import com.caretrack.core.medication.dto.MedicationIntakeDto;
import com.caretrack.core.medication.service.MedicationIntakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/medication-intakes")
@RequiredArgsConstructor
@Tag(name = "Medication intakes", description = "Medication intake tracking endpoints")
public class MedicationIntakeController {

    private final MedicationIntakeService medicationIntakeService;

    @GetMapping
    @Operation(summary = "List medication intakes filtered by patient or plan")
    public ResponseEntity<List<MedicationIntakeDto>> findAll(
            @RequestParam(value = "patientId", required = false) Long patientId,
            @RequestParam(value = "planId", required = false) Long planId) {
        return ResponseEntity.ok(medicationIntakeService.findByFilters(patientId, planId));
    }

    @PostMapping
    @Operation(summary = "Create a medication intake")
    public ResponseEntity<MedicationIntakeDto> create(@RequestBody @Validated MedicationIntakeDto dto) {
        MedicationIntakeDto created = medicationIntakeService.create(dto);
        return ResponseEntity.created(URI.create("/api/medication-intakes/" + created.getId())).body(created);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update the status of a medication intake")
    public ResponseEntity<MedicationIntakeDto> updateStatus(
            @PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        try {
            return ResponseEntity.ok(medicationIntakeService.updateStatus(id, request.status()));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    public record UpdateStatusRequest(@jakarta.validation.constraints.NotBlank String status) {}
}
