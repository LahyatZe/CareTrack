package com.caretrack.api.alert;

import com.caretrack.core.alert.dto.AlertDto;
import com.caretrack.core.alert.service.AlertService;
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

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@Tag(name = "Alerts", description = "Alert management endpoints")
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    @Operation(summary = "List alerts filtered by status or patient")
    public ResponseEntity<List<AlertDto>> findAlerts(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "patientId", required = false) Long patientId) {
        return ResponseEntity.ok(alertService.findByFilters(status, patientId));
    }

    @PostMapping
    @Operation(summary = "Create an alert")
    public ResponseEntity<AlertDto> create(@RequestBody @Validated AlertDto dto) {
        AlertDto created = alertService.create(dto);
        return ResponseEntity.created(URI.create("/api/alerts/" + created.getId())).body(created);
    }

    @PatchMapping("/{id}/acknowledge")
    @Operation(summary = "Acknowledge an alert")
    public ResponseEntity<AlertDto> acknowledge(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.acknowledge(id));
    }

    @PatchMapping("/{id}/resolve")
    @Operation(summary = "Resolve an alert")
    public ResponseEntity<AlertDto> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.resolve(id));
    }
}
