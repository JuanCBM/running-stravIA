package com.wikiblaz.straviarunning.rest.mapper;

import com.wikiblaz.straviarunning.domain.model.Activity;
import com.wikiblaz.straviarunning.domain.model.ActivityType;
import com.wikiblaz.straviarunning.rest.model.ActivityRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * MapStruct mapper for converting between domain and REST models
 */
@Mapper(componentModel = "spring")
public interface ActivityMapper {

    /**
     * Convert domain Activity to REST Activity
     */
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    com.wikiblaz.straviarunning.rest.model.Activity toRestActivity(Activity domainActivity);

    /**
     * Convert REST ActivityRequest to domain Activity
     */
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "elevationGain", source = "elevationGain", defaultValue = "0.0")
    Activity toDomainActivity(ActivityRequest restActivityRequest);

    /**
     * Convert domain ActivityType to REST ActivityType
     */
    default com.wikiblaz.straviarunning.rest.model.ActivityType toRestActivityType(ActivityType domainType) {
        if (domainType == null) {
            return null;
        }
        return com.wikiblaz.straviarunning.rest.model.ActivityType.valueOf(domainType.name());
    }

    /**
     * Convert REST ActivityType to domain ActivityType
     */
    default ActivityType toDomainActivityType(com.wikiblaz.straviarunning.rest.model.ActivityType restType) {
        if (restType == null) {
            return null;
        }
        return ActivityType.valueOf(restType.name());
    }

    /**
     * Convert LocalDateTime to OffsetDateTime
     */
    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
    }

    /**
     * Convert OffsetDateTime to LocalDateTime
     */
    @Named("offsetDateTimeToLocalDateTime")
    default LocalDateTime offsetDateTimeToLocalDateTime(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return LocalDateTime.ofInstant(offsetDateTime.toInstant(), ZoneOffset.UTC);
    }
}