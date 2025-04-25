package com.wikiblaz.straviarunning.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikiblaz.straviarunning.domain.out.StravaActivityPort;
import com.wikiblaz.straviarunning.domain.out.StravaAuthPort;
import com.wikiblaz.straviarunning.rest.api.StravaApi;
import com.wikiblaz.straviarunning.rest.mapper.ActivityMapper;
import com.wikiblaz.straviarunning.rest.model.Activity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StravaActivityController implements StravaApi {
    private final StravaActivityPort stravaActivityPort;
    private final StravaAuthPort stravaAuthPort;
    private final ActivityMapper activityMapper;
    private final ObjectMapper objectMapper;

    @Value("${strava.frontend.url:http://localhost:4200}")
    private String frontendUrl;

    public StravaActivityController(StravaActivityPort stravaActivityPort, StravaAuthPort stravaAuthPort, ActivityMapper activityMapper) {
        this.stravaActivityPort = stravaActivityPort;
        this.stravaAuthPort = stravaAuthPort;
        this.activityMapper = activityMapper;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/strava/activities")
    public ResponseEntity<List<Activity>> _getStravaActivities(@RequestParam("token") String accessToken) {
        List<com.wikiblaz.straviarunning.domain.model.Activity> userActivities = stravaActivityPort.getUserActivities(accessToken);
        List<Activity> restActivities = userActivities.stream()
            .map(activityMapper::toRestActivity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(restActivities);
    }

    @GetMapping("/strava/auth")
    public ResponseEntity<String> _getStravaAuthUrl() {
        return ResponseEntity.ok(stravaAuthPort.generateAuthUrl());
    }

    @GetMapping("/strava/callback")
    public ResponseEntity<String> _handleStravaCallback(@RequestParam("code") String code) {
        try {
            String tokenResponse = stravaAuthPort.exchangeCodeForToken(code);

            // Parse the JSON response to extract the access token
            JsonNode jsonNode = objectMapper.readTree(tokenResponse);
            String accessToken = jsonNode.get("access_token").asText();

            // Create a redirect to the frontend with the token as a parameter
            String redirectUrl = frontendUrl + "/auth-success?token=" + accessToken;
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", redirectUrl)
                    .build();
        } catch (IOException | InterruptedException e) {
            // In case of error, redirect to the frontend with an error parameter
            String errorUrl = frontendUrl + "/auth-error?message=" + e.getMessage();
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", errorUrl)
                    .build();
        }
    }
}
