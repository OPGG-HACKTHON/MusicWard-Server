package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistSaveRequest;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistUpdateRequest;
import io.github.opgg.music_ward_server.service.playlist.PlaylistService;
import io.github.opgg.music_ward_server.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/playlists/{playlistId}")
    public ResponseEntity<CommonResponse> update(@PathVariable("playlistId") Long playlistId,
                                                 @RequestBody PlaylistUpdateRequest updateDto) {

        playlistService.update(playlistId, updateDto);

        return new ResponseEntity<>(new CommonResponse(playlistService.findById(playlistId)), HttpStatus.OK);
    }

    @PostMapping("/playlists/{playlistId}")
    public ResponseEntity<CommonResponse> synchronize(@PathVariable("playlistId") Long playlistId) {

        playlistService.synchronize(playlistId);

        return new ResponseEntity<>(new CommonResponse(playlistService.findById(playlistId)), HttpStatus.OK);
    }

    @GetMapping("users/{userId}/playlists")
    public ResponseEntity<CommonResponse> findByUserId(@PathVariable("userId") Long userId) {

        return new ResponseEntity<>(new CommonResponse(playlistService.findByUserId(userId)), HttpStatus.OK);
    }

    @GetMapping("me/playlists")
    public ResponseEntity<CommonResponse> findByMe() {

        Long userId = SecurityUtil.getCurrentUserId();

        return new ResponseEntity<>(new CommonResponse(playlistService.findByUserId(userId)), HttpStatus.OK);
    }
}