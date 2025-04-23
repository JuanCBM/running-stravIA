package com.wikiblaz.straviarunning.strava.adapter;

import com.wikiblaz.straviarunning.domain.out.StravaAuthPort;
import com.wikiblaz.straviarunning.strava.config.StravaConfig;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Component;

@Component
public class StravaAuthAuthAdapter implements StravaAuthPort {

  private final StravaConfig stravaConfig;

  public StravaAuthAuthAdapter(StravaConfig stravaConfig) {
    this.stravaConfig = stravaConfig;
  }

  @Override
  public String generateAuthUrl() {
    return "https://www.strava.com/oauth/authorize"
        + "?client_id="
        + stravaConfig.clientId
        + "&redirect_uri="
        + stravaConfig.redirectUri
        + "&response_type=code"
        + "&scope=activity:read_all";
  }

  @Override
  public String exchangeCodeForToken(String code) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    String body =
        "client_id="
            + stravaConfig.clientId
            + "&client_secret="
            + stravaConfig.clientSecret
            + "&code="
            + code
            + "&grant_type=authorization_code";

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("https://www.strava.com/oauth/token"))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }
}
