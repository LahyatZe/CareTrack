package com.caretrack.api.treatmentplan;

import com.caretrack.core.treatmentplan.dto.TreatmentPlanDto;
import com.caretrack.core.treatmentplan.dto.TreatmentStepDto;
import com.caretrack.core.treatmentplan.service.TreatmentPlanService;
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
@RequestMapping("/api/plans")
@RequiredArgsConstructor
@Tag(name = "Treatment plans", description = "Treatment plan management endpoints")
public class TreatmentPlanController {

    private final TreatmentPlanService treatmentPlanService;

    @PostMapping
    @Operation(summary = "Create a treatment plan")
    public ResponseEntity<TreatmentPlanDto> create(@RequestBody @Validated TreatmentPlanDto dto) {
        TreatmentPlanDto created = treatmentPlanService.create(dto);
        return ResponseEntity.created(URI.create("/api/plans/" + created.getId())).body(created);
    }

    @GetMapping
    @Operation(summary = "List all treatment plans")
    public ResponseEntity<List<TreatmentPlanDto>> findAll() {
        return ResponseEntity.ok(treatmentPlanService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a treatment plan by id")
    public ResponseEntity<TreatmentPlanDto> findById(@PathVariable Long id) {
        return treatmentPlanService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a treatment plan")
    public ResponseEntity<TreatmentPlanDto> update(@PathVariable Long id, @RequestBody @Validated TreatmentPlanDto dto) {
        return ResponseEntity.ok(treatmentPlanService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a treatment plan")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        treatmentPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/steps")
    @Operation(summary = "Add a step to a treatment plan")
    public ResponseEntity<TreatmentStepDto> addStep(@PathVariable Long id, @RequestBody @Validated TreatmentStepDto dto) {
        return ResponseEntity.ok(treatmentPlanService.addStep(id, dto));
    }
}
