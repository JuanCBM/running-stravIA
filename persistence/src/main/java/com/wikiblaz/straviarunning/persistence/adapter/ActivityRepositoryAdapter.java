package com.wikiblaz.straviarunning.persistence.adapter;

import com.wikiblaz.straviarunning.domain.model.Activity;
import com.wikiblaz.straviarunning.domain.out.ActivityPort;
import com.wikiblaz.straviarunning.persistence.entity.ActivityEntity;
import com.wikiblaz.straviarunning.persistence.repository.ActivityJpaRepository;
import com.wikiblaz.straviarunning.persistence.mapper.ActivityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementation of the ActivityRepository interface
 * This adapter uses the JPA repository and maps between domain and persistence models
 */
@Component
@RequiredArgsConstructor
public class ActivityRepositoryAdapter implements ActivityPort {
    
    private final ActivityJpaRepository activityJpaRepository;
    private final ActivityMapper activityMapper;
    
    @Override
    public Activity save(Activity activity) {
        ActivityEntity entity = activityMapper.toEntity(activity);
        ActivityEntity savedEntity = activityJpaRepository.save(entity);
        return activityMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Activity> findById(String id) {
        return activityJpaRepository.findById(id)
                .map(activityMapper::toDomain);
    }
    
    @Override
    public List<Activity> findAll() {
        return activityJpaRepository.findAll().stream()
                .map(activityMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(String id) {
        activityJpaRepository.deleteById(id);
    }
}