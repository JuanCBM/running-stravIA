package com.wikiblaz.straviarunning.application.service;

import com.wikiblaz.straviarunning.application.port.in.ActivityInfoUseCase;
import com.wikiblaz.straviarunning.application.port.in.ActivityUseCase;
import com.wikiblaz.straviarunning.domain.model.Activity;
import com.wikiblaz.straviarunning.domain.out.ActivityPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.wikiblaz.straviarunning.domain.out.StravaActivityPort;
import com.wikiblaz.straviarunning.domain.out.StravaAuthPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the ActivityUseCase interface
 * This service implements the use cases defined in the interface
 */
@Service
@RequiredArgsConstructor
public class ActivityInfoService implements ActivityInfoUseCase {

    private final StravaAuthPort stravaAuthPort;
    private final StravaActivityPort stravaActivityPort;

    @Override
    public String generateAuthUrl() {
        return stravaAuthPort.generateAuthUrl();
    }

    @Override
    public List<Activity> getUserActivities(String accessToken) {
        return stravaActivityPort.getUserActivities(accessToken);
    }
}