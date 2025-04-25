package com.wikiblaz.straviarunning.strava.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wikiblaz.straviarunning.domain.model.Activity;
import com.wikiblaz.straviarunning.domain.model.ActivityType;
import com.wikiblaz.straviarunning.domain.out.StravaActivityPort;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class StravaActivityAdapter implements StravaActivityPort {

  private final ObjectMapper objectMapper;

  public StravaActivityAdapter() {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule());
  }

  @Override
  public List<Activity> getUserActivities(String accessToken) {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("https://www.strava.com/api/v3/athlete/activities"))
            .header("Authorization", "Bearer " + accessToken)
            .GET()
            .build();

    HttpResponse<String> response;
    try {
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return parseActivities(response.body());
    } catch (IOException | InterruptedException e) {
      return List.of();
    }
  }

  private List<Activity> parseActivities(String json) {
    try {
      List<Map<String, Object>> activities = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
      List<Activity> result = new ArrayList<>();

      for (Map<String, Object> activityData : activities) {
        ActivityType type = mapActivityType((String) activityData.get("type"));

        LocalDateTime startDate = null;
        if (activityData.get("start_date") != null) {
          startDate = LocalDateTime.parse(((String) activityData.get("start_date")).replace("Z", ""));
        }

        // Calculate end date based on start date and elapsed time
        LocalDateTime endDate = null;
        if (startDate != null && activityData.get("elapsed_time") != null) {
          int elapsedSeconds = ((Number) activityData.get("elapsed_time")).intValue();
          endDate = startDate.plusSeconds(elapsedSeconds);
        }

        Activity activity = Activity.builder()
            .id(String.valueOf(activityData.get("id")))
            .name((String) activityData.get("name"))
            .description((String) activityData.get("description"))
            .distance(((Number) activityData.get("distance")).doubleValue())
            .duration(((Number) activityData.get("elapsed_time")).longValue())
            .startDate(startDate)
            .endDate(endDate)
            .elevationGain(activityData.get("total_elevation_gain") != null ? 
                ((Number) activityData.get("total_elevation_gain")).doubleValue() : 0)
            .type(type)
            .build();

        result.add(activity);
      }

      return result;
    } catch (JsonProcessingException e) {
      return List.of();
    }
  }

  private ActivityType mapActivityType(String stravaType) {
    if (stravaType == null) {
      return ActivityType.RUN; // Default to OTHER if type is null
    }

      return switch (stravaType.toUpperCase()) {
          case "RUN" -> ActivityType.RUN;
          case "TRAIL_RUN" -> ActivityType.TRAIL_RUN;
          case "TRACK_RUN" -> ActivityType.TRACK_RUN;
          case "TREADMILL" -> ActivityType.TREADMILL;
          case "RACE" -> ActivityType.RACE;
          case "WALK" -> ActivityType.WALK;
          case "HIKE" -> ActivityType.HIKE;
          default -> ActivityType.OTHER; // Default to OTHER for unmapped types
      };
  }
}
