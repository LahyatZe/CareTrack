package com.caretrack.security.jwt;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service responsible for generating and validating JWT tokens. It supports issuing
 * independent access and refresh tokens, each with their own expiration time.
 */
@Service
public class JwtTokenService {

    private static final String TOKEN_TYPE_CLAIM = "token_type";
    private static final String AUTHORITIES_CLAIM = "roles";

    private final JwtProperties properties;
    private final SecretKey secretKey;

    public JwtTokenService(JwtProperties properties) {
        this.properties = properties;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.secret()));
    }

    public TokenPair generateTokenPair(UserDetails userDetails) {
        Instant now = Instant.now();
        Instant accessExpiresAt = now.plus(properties.accessTokenTtl());
        Instant refreshExpiresAt = now.plus(properties.refreshTokenTtl());

        String accessToken = buildToken(userDetails, accessExpiresAt, TokenType.ACCESS);
        String refreshToken = buildToken(userDetails, refreshExpiresAt, TokenType.REFRESH);

        return new TokenPair(accessToken, accessExpiresAt, refreshToken, refreshExpiresAt);
    }

    public String buildToken(UserDetails userDetails, Instant expiration, TokenType tokenType) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiration))
                .addClaims(Map.of(
                        TOKEN_TYPE_CLAIM, tokenType.name(),
                        AUTHORITIES_CLAIM, extractAuthorities(userDetails)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails, TokenType expectedType) {
        Claims claims = parseClaims(token);
        String username = claims.getSubject();
        if (!userDetails.getUsername().equals(username)) {
            return false;
        }
        if (!expectedType.name().equals(claims.get(TOKEN_TYPE_CLAIM, String.class))) {
            return false;
        }
        return !isTokenExpired(claims);
    }

    public boolean isRefreshToken(String token) {
        Claims claims = parseClaims(token);
        return TokenType.REFRESH.name().equals(claims.get(TOKEN_TYPE_CLAIM, String.class))
                && !isTokenExpired(claims);
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    private Set<String> extractAuthorities(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }
}
