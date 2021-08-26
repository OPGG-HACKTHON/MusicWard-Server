package io.github.opgg.music_ward_server.utils.api.client.spotify;

import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyPlaylistResponse;
import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyPlaylistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "spotifyApiClient", url = "https://api.spotify.com")
public interface SpotifyApiClient {

    @GetMapping("v1/playlists/{playlistId}")
    SpotifyPlaylistResponse getPlaylist(@RequestHeader("Authorization") String token,
                                        @PathVariable("playlistId") String playlistId);

    @GetMapping("v1/me/playlists")
    SpotifyPlaylistsResponse getPlaylists(@RequestHeader("Authorization") String token);
}