package com.wikiblaz.straviarunning.application.service;

import com.wikiblaz.straviarunning.application.port.in.ActivityUseCase;
import com.wikiblaz.straviarunning.domain.model.Activity;
import com.wikiblaz.straviarunning.domain.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the ActivityUseCase interface
 * This service implements the use cases defined in the interface
 */
@Service
@RequiredArgsConstructor
public class ActivityService implements ActivityUseCase {
    
    private final ActivityRepository activityRepository;
    
    @Override
    public Activity createActivity(Activity activity) {
        if (activity.getId() == null || activity.getId().isEmpty()) {
            activity.setId(UUID.randomUUID().toString());
        }
        return activityRepository.save(activity);
    }
    
    @Override
    public Optional<Activity> getActivityById(String id) {
        return activityRepository.findById(id);
    }
    
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
    
    @Override
    public Activity updateActivity(String id, Activity activity) {
        activity.setId(id);
        return activityRepository.save(activity);
    }
    
    @Override
    public void deleteActivity(String id) {
        activityRepository.deleteById(id);
    }
}