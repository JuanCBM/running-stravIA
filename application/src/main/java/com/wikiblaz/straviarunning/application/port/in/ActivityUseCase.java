package com.wikiblaz.straviarunning.application.port.in;

import com.wikiblaz.straviarunning.domain.model.Activity;

import java.util.List;
import java.util.Optional;

/**
 * Input port for Activity use cases
 * This interface defines the operations that can be performed on activities
 */
public interface ActivityUseCase {
    
    /**
     * Create a new activity
     * @param activity the activity to create
     * @return the created activity
     */
    Activity createActivity(Activity activity);
    
    /**
     * Get an activity by its id
     * @param id the activity id
     * @return the activity if found
     */
    Optional<Activity> getActivityById(String id);
    
    /**
     * Get all activities
     * @return list of all activities
     */
    List<Activity> getAllActivities();
    
    /**
     * Update an existing activity
     * @param id the activity id
     * @param activity the updated activity data
     * @return the updated activity
     */
    Activity updateActivity(String id, Activity activity);
    
    /**
     * Delete an activity
     * @param id the activity id to delete
     */
    void deleteActivity(String id);
}