package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.dto.playlist.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.service.playlist.PlaylistService;
import io.github.opgg.music_ward_server.utils.api.dto.google.YoutubePlaylistsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/")
@RequiredArgsConstructor
@RestController
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("non-playlists")
    public ResponseEntity<CommonResponse> getNonPlaylists() {

        NonPlaylistsResponse nonPlaylists = playlistService.getNonPlaylists();

        return ResponseEntity.ok(new CommonResponse(nonPlaylists));
    }
}