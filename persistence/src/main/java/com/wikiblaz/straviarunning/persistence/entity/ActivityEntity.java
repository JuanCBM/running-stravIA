package com.wikiblaz.straviarunning.persistence.entity;

import com.wikiblaz.straviarunning.domain.model.ActivityType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA entity for Activity
 */
@Entity
@Table(name = "activities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private double distance;
    private long duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double elevationGain;
    
    @Enumerated(EnumType.STRING)
    private ActivityType type;
}