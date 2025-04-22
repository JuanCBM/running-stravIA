package com.wikiblaz.straviarunning.rest;

import com.wikiblaz.straviarunning.application.port.in.ActivityUseCase;
import com.wikiblaz.straviarunning.domain.model.Activity;
import com.wikiblaz.straviarunning.rest.api.ActivitiesApi;
import com.wikiblaz.straviarunning.rest.mapper.ActivityMapper;
import com.wikiblaz.straviarunning.rest.model.ActivityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for Activity endpoints
 */
@RestController
@RequiredArgsConstructor
public class ActivityController implements ActivitiesApi {

    private final ActivityUseCase activityUseCase;
    private final ActivityMapper activityMapper;

    @Override
    public ResponseEntity<com.wikiblaz.straviarunning.rest.model.Activity> _createActivity(ActivityRequest activityRequest) {
        Activity domainActivity = activityMapper.toDomainActivity(activityRequest);
        Activity createdActivity = activityUseCase.createActivity(domainActivity);
        return new ResponseEntity<>(activityMapper.toRestActivity(createdActivity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> _deleteActivity(String id) {
        activityUseCase.deleteActivity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<com.wikiblaz.straviarunning.rest.model.Activity> _getActivityById(String id) {
        return activityUseCase.getActivityById(id)
                .map(activity -> new ResponseEntity<>(activityMapper.toRestActivity(activity), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<com.wikiblaz.straviarunning.rest.model.Activity>> _getAllActivities() {
        List<Activity> domainActivities = activityUseCase.getAllActivities();
        List<com.wikiblaz.straviarunning.rest.model.Activity> restActivities = domainActivities.stream()
                .map(activityMapper::toRestActivity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(restActivities, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<com.wikiblaz.straviarunning.rest.model.Activity> _updateActivity(String id, ActivityRequest activityRequest) {
        Activity domainActivity = activityMapper.toDomainActivity(activityRequest);
        Activity updatedActivity = activityUseCase.updateActivity(id, domainActivity);
        return new ResponseEntity<>(activityMapper.toRestActivity(updatedActivity), HttpStatus.OK);
    }

}
