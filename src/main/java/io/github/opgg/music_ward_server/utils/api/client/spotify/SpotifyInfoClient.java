package io.github.opgg.music_ward_server.utils.api.client.spotify;

import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "spotifyInfoClient", url = "https://api.spotify.com")
public interface SpotifyInfoClient {

    @GetMapping("/v1/me")
    SpotifyEmailResponse getEmail(@RequestHeader("Authorization") String token);

}
