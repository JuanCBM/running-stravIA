package com.wikiblaz.straviarunning.persistence.repository;

import com.wikiblaz.straviarunning.persistence.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ActivityEntity
 */
@Repository
public interface ActivityJpaRepository extends JpaRepository<ActivityEntity, String> {
}