package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistSaveRequest;
import io.github.opgg.music_ward_server.service.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

        return new ResponseEntity<>(new CommonResponse(playlistService.getNonPlaylists()), HttpStatus.OK);
    }

    @PostMapping("/playlists")
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody PlaylistSaveRequest requestDto) {

        return new ResponseEntity<>(new CommonResponse(playlistService.save(requestDto)), HttpStatus.CREATED);
    }

    @GetMapping("/playlists")
    public ResponseEntity<CommonResponse> findAll() {

        return new ResponseEntity<>(new CommonResponse(playlistService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/playlists/{playlistId}")
    public ResponseEntity<CommonResponse> findById(@PathVariable("playlistId") Long playlistId) {

        return new ResponseEntity<>(new CommonResponse(playlistService.findById(playlistId)), HttpStatus.OK);
    }
}