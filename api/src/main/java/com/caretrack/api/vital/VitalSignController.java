package com.caretrack.api.vital;

import com.caretrack.core.vital.dto.VitalSignDto;
import com.caretrack.core.vital.service.VitalSignService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vitals")
@RequiredArgsConstructor
@Tag(name = "Vitals", description = "Vital signs endpoints")
public class VitalSignController {

    private final VitalSignService vitalSignService;

    @GetMapping
    @Operation(summary = "List vital signs for a patient")
    public ResponseEntity<List<VitalSignDto>> findByPatient(@RequestParam("patientId") Long patientId) {
        return ResponseEntity.ok(vitalSignService.findByPatient(patientId));
    }

    @PostMapping
    @Operation(summary = "Create a vital sign record")
    public ResponseEntity<VitalSignDto> create(@RequestBody @Validated VitalSignDto dto) {
        VitalSignDto created = vitalSignService.create(dto);
        return ResponseEntity.created(URI.create("/api/vitals/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a vital sign by id")
    public ResponseEntity<VitalSignDto> findById(@PathVariable Long id) {
        return vitalSignService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vital sign record")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vitalSignService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
