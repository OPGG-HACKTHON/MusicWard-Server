package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.dto.playlist.response.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistSaveRequest;
import io.github.opgg.music_ward_server.service.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @PostMapping("/playlists")
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody PlaylistSaveRequest requestDto) {

        return ResponseEntity.ok(new CommonResponse(playlistService.save(requestDto)));
    }

    @GetMapping("/playlists")
    public ResponseEntity<CommonResponse> findAll() {

        return ResponseEntity.ok(new CommonResponse(playlistService.findAll()));
    }

    @GetMapping("/playlists/{playlistId}")
    public ResponseEntity<CommonResponse> findById(@PathVariable("playlistId") Long playlistId) {

        return ResponseEntity.ok(new CommonResponse(playlistService.findById(playlistId)));
    }
}