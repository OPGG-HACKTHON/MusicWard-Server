package io.github.opgg.music_ward_server.utils.api.client;

import io.github.opgg.music_ward_server.utils.api.dto.google.CodeRequest;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "googleAuthClient", url = "https://oauth2.googleapis.com")
public interface GoogleAuthClient {

    @PostMapping(value = "/token")
    GoogleTokenResponse getTokenByCode(CodeRequest request);

}
