package com.wikiblaz.straviarunning.rest;

import com.wikiblaz.straviarunning.domain.out.StravaActivityPort;
import com.wikiblaz.straviarunning.domain.out.StravaAuthPort;
import com.wikiblaz.straviarunning.rest.api.StravaApi;
import com.wikiblaz.straviarunning.rest.mapper.ActivityMapper;
import com.wikiblaz.straviarunning.rest.model.Activity;
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

    public StravaActivityController(StravaActivityPort stravaActivityPort, StravaAuthPort stravaAuthPort, ActivityMapper activityMapper) {
        this.stravaActivityPort = stravaActivityPort;
        this.stravaAuthPort = stravaAuthPort;
        this.activityMapper = activityMapper;
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
            return ResponseEntity.ok(tokenResponse);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.badRequest().body("Error exchanging code for token: " + e.getMessage());
        }
    }
}
