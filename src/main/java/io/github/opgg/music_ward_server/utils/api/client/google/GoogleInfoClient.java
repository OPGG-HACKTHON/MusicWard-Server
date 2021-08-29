package io.github.opgg.music_ward_server.utils.api.client.google;

import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "googleInfoClient", url = "https://openidconnect.googleapis.com")
public interface GoogleInfoClient {

    @GetMapping("/v1/userinfo")
    GoogleInfoResponse getEmail(@RequestHeader("Authorization") String accessToken);

}
