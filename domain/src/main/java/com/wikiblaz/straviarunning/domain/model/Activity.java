package com.wikiblaz.straviarunning.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Domain entity representing a running activity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private String id;
    private String name;
    private String description;
    private double distance; // in meters
    private long duration; // in seconds
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double elevationGain; // in meters
    private ActivityType type;
}