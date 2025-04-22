package com.wikiblaz.straviarunning.persistence.mapper;

import com.wikiblaz.straviarunning.domain.model.Activity;
import com.wikiblaz.straviarunning.persistence.entity.ActivityEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Activity domain model and ActivityEntity JPA entity
 */
@Component
public class ActivityMapper {
    
    /**
     * Convert domain model to JPA entity
     * @param activity domain model
     * @return JPA entity
     */
    public ActivityEntity toEntity(Activity activity) {
        if (activity == null) {
            return null;
        }
        
        return ActivityEntity.builder()
                .id(activity.getId())
                .name(activity.getName())
                .description(activity.getDescription())
                .distance(activity.getDistance())
                .duration(activity.getDuration())
                .startDate(activity.getStartDate())
                .endDate(activity.getEndDate())
                .elevationGain(activity.getElevationGain())
                .type(activity.getType())
                .build();
    }
    
    /**
     * Convert JPA entity to domain model
     * @param entity JPA entity
     * @return domain model
     */
    public Activity toDomain(ActivityEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Activity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .distance(entity.getDistance())
                .duration(entity.getDuration())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .elevationGain(entity.getElevationGain())
                .type(entity.getType())
                .build();
    }
}