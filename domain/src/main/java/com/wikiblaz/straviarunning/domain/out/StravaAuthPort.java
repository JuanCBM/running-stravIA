package com.wikiblaz.straviarunning.domain.out;

import java.io.IOException;

public interface StravaAuthPort {

    String generateAuthUrl();

    String exchangeCodeForToken(String code) throws IOException, InterruptedException;

}
