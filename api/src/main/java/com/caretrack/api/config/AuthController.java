package com.caretrack.api.config;

import com.caretrack.security.user.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
@Validated
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and issue a JWT token")
    public ResponseEntity<TokenResponse> login(@RequestBody @jakarta.validation.Valid LoginRequest request) {
        String token = authenticationService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh a JWT token")
    public ResponseEntity<TokenResponse> refresh(@RequestBody @jakarta.validation.Valid RefreshRequest request) {
        String token = authenticationService.refreshToken(request.getUsername());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @Value
    public static class LoginRequest {
        @NotBlank
        String username;
        @NotBlank
        String password;
    }

    @Value
    public static class RefreshRequest {
        @NotBlank
        String username;
    }

    @Value
    public static class TokenResponse {
        String token;
    }
}
