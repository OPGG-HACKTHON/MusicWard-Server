package io.github.opgg.music_ward_server.utils.api.client;

import io.github.opgg.music_ward_server.utils.api.dto.google.YoutubePlaylistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleApiClient", url = "https://www.googleapis.com")
public interface GoogleApiClient {

    @GetMapping("/youtube/v3/playlists")
    YoutubePlaylistsResponse getPlaylists(@RequestHeader("Authorization") String token,
                                          @RequestParam("part") String part,
                                          @RequestParam("mine") boolean mine);
}