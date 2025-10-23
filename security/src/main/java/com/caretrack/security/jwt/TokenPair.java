package com.caretrack.security.jwt;

import java.time.Instant;

/**
 * Value object used to return both access and refresh tokens with their expiration
 * timestamps. It is useful both in the authentication filter and in the refresh endpoint.
 */
public record TokenPair(
        String accessToken,
        Instant accessTokenExpiresAt,
        String refreshToken,
        Instant refreshTokenExpiresAt
) {
}
