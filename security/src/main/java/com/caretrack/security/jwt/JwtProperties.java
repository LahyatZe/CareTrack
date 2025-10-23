package com.caretrack.security.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Configuration properties used for the JWT infrastructure. Defaults are provided so the
 * application works out of the box, but they can be overridden in the runtime configuration.
 */
@Validated
@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(
        @NotBlank String secret,
        Duration accessTokenTtl,
        Duration refreshTokenTtl
) {

    public JwtProperties(@NotBlank String secret,
                         @DefaultValue("PT15M") Duration accessTokenTtl,
                         @DefaultValue("P7D") Duration refreshTokenTtl) {
        this.secret = secret;
        this.accessTokenTtl = accessTokenTtl;
        this.refreshTokenTtl = refreshTokenTtl;
    }
}
