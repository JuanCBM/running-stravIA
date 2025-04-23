package com.wikiblaz.straviarunning.domain.out;

import com.wikiblaz.straviarunning.domain.model.Activity;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Activity domain entity
 * This is a port in the hexagonal architecture
 */
public interface ActivityPort {
    
    /**
     * Save an activity
     * @param activity the activity to save
     * @return the saved activity
     */
    Activity save(Activity activity);
    
    /**
     * Find an activity by its id
     * @param id the activity id
     * @return the activity if found
     */
    Optional<Activity> findById(String id);
    
    /**
     * Find all activities
     * @return list of all activities
     */
    List<Activity> findAll();
    
    /**
     * Delete an activity
     * @param id the activity id to delete
     */
    void deleteById(String id);
}