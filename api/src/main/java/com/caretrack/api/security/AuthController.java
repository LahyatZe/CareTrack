package com.caretrack.api.security;

import com.caretrack.security.jwt.JwtTokenService;
import com.caretrack.security.jwt.TokenPair;
import com.caretrack.security.jwt.TokenResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenService tokenService;
    private final UserDetailsService userDetailsService;

    public AuthController(JwtTokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        try {
            if (!tokenService.isRefreshToken(refreshToken)) {
                throw new ResponseStatusException(UNAUTHORIZED, "Invalid refresh token");
            }
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(UNAUTHORIZED, "Invalid refresh token", ex);
        }

        String username;
        try {
            username = tokenService.extractUsername(refreshToken);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(UNAUTHORIZED, "Invalid refresh token", ex);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        TokenPair tokenPair = tokenService.generateTokenPair(userDetails);

        return ResponseEntity.ok(TokenResponse.from(tokenPair));
    }

    public record RefreshTokenRequest(@NotBlank String refreshToken) {
    }
}
