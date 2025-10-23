package com.caretrack.api.user;

import com.caretrack.core.user.dto.UserCreateDto;
import com.caretrack.core.user.dto.UserDto;
import com.caretrack.core.user.dto.UserUpdateDto;
import com.caretrack.core.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User and team management endpoints")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get the current user's profile")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    @PutMapping("/me")
    @Operation(summary = "Update the current user's profile")
    public ResponseEntity<UserDto> updateCurrentUser(Principal principal, @RequestBody @Valid UserUpdateDto updateDto) {
        return ResponseEntity.ok(userService.updateProfile(principal.getName(), updateDto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List all users")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserCreateDto createDto) {
        UserDto created = userService.create(createDto);
        return ResponseEntity.created(URI.create("/api/users/" + created.getId())).body(created);
    }

    @PatchMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user roles")
    public ResponseEntity<UserDto> updateRoles(@PathVariable Long id, @RequestBody @Valid UpdateRolesRequest request) {
        return ResponseEntity.ok(userService.updateRoles(id, request.roles()));
    }

    public record UpdateRolesRequest(@NotEmpty Set<String> roles) {}
}
