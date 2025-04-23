package com.wikiblaz.straviarunning.application.port.in;

import com.wikiblaz.straviarunning.domain.model.Activity;
import java.util.List;

public interface ActivityInfoUseCase {
  String generateAuthUrl();

  List<Activity> getUserActivities(String accessToken);
}
