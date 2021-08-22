package io.github.opgg.music_ward_server.utils.api.client.spotify;

import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spotifyAuthClient", url = "https://accounts.spotify.com")
public interface SpotifyAuthClient {

    @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SpotifyTokenResponse getTokenByCode(@RequestParam("grant_type") String grantType,
                                        @RequestParam("code") String code,
                                        @RequestParam("redirect_uri") String redirectUri,
                                        @RequestHeader("Authorization") String authorization);

}
