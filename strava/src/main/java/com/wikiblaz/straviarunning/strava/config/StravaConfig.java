package com.wikiblaz.straviarunning.strava.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

/**
 * Configuration for Strava API integration.
 * This class will provide the necessary beans for Strava API integration.
 */
@Configuration
public class StravaConfig {

    @Value("${strava.client.id:}")
    public String clientId;

    @Value("${strava.client.secret:}")
    public String clientSecret;

    @Value("${strava.redirect.uri:}")
    public String redirectUri;

    @Value("${strava.frontend.url:http://localhost:4200}")
    public String frontendUrl;
}
