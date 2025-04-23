package com.wikiblaz.straviarunning.domain.out;

import com.wikiblaz.straviarunning.domain.model.Activity;
import java.util.List;

public interface StravaActivityPort {
  List<Activity> getUserActivities(String accessToken);
}
