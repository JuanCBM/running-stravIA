package com.wikiblaz.straviarunning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web configuration for the application.
 * Handles CORS (Cross-Origin Resource Sharing) settings.
 * Configured to allow all origins without any limitations.
 */
@Component
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Allow all origins without any limitations
    private static final String ALL_ORIGINS = "*";

    private static final List<String> ALLOWED_METHODS = List.of(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
    );

    private static final long MAX_AGE = 3600L;

    /**
     * Configure CORS mappings for Spring MVC.
     * Allows all origins without any limitations.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(ALL_ORIGINS)
                .allowedMethods(ALLOWED_METHODS.toArray(new String[0]))
                .allowedHeaders("*")
                .allowCredentials(false) // Must be false when using "*" for origins
                .maxAge(MAX_AGE);
    }

}
