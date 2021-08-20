package io.github.opgg.music_ward_server.utils.api.client;

import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "googleInfoClient", url = "https://www.googleapis.com/oauth2")
public interface GoogleInfoClient {

    @GetMapping("/v3/userinfo")
    GoogleEmailResponse getEmail(@RequestParam("access_token") String accessToken);

}
