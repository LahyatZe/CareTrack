package com.caretrack.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI caretrackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CareTrack API")
                        .description("CareTrack platform REST API documentation")
                        .version("v1"));
    }
}
