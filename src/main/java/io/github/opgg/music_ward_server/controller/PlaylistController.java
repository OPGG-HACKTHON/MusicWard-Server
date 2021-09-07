package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.controller.response.PageResponse;
import io.github.opgg.music_ward_server.dto.page.request.PageMainRequest;
import io.github.opgg.music_ward_server.dto.page.response.PageInfoResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistReportRequest;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistSaveRequest;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistUpdateRequest;
import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import io.github.opgg.music_ward_server.service.playlist.PlaylistService;
import io.github.opgg.music_ward_server.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/")
@RequiredArgsConstructor
@RestController
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("non-playlists")
    public ResponseEntity<CommonResponse> getNonPlaylists(@RequestParam(value = "provider") String provider) {

        return new ResponseEntity<>(new CommonResponse(playlistService.getNonPlaylists(provider)), HttpStatus.OK);
    }

    @PostMapping("playlists")
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody PlaylistSaveRequest requestDto) {

        return new ResponseEntity<>(new CommonResponse(playlistService.save(requestDto)), HttpStatus.CREATED);
    }

    @GetMapping("playlists/{playlistId}")
    public ResponseEntity<CommonResponse> findById(@PathVariable("playlistId") Long playlistId) {

        playlistService.addView(playlistId);

        return new ResponseEntity<>(new CommonResponse(playlistService.findById(playlistId)), HttpStatus.OK);
    }

    @PutMapping("playlists/{playlistId}")
    public ResponseEntity<CommonResponse> update(@PathVariable("playlistId") Long playlistId,
                                                 @RequestBody PlaylistUpdateRequest updateDto) {

        playlistService.update(playlistId, updateDto);

        return new ResponseEntity<>(new CommonResponse(playlistService.findById(playlistId)), HttpStatus.OK);
    }

    @PostMapping("playlists/{playlistId}")
    public ResponseEntity<CommonResponse> synchronize(@PathVariable("playlistId") Long playlistId) {

        playlistService.synchronize(playlistId);

        return new ResponseEntity<>(new CommonResponse(playlistService.findById(playlistId)), HttpStatus.OK);
    }

    @DeleteMapping("playlists/{playlistId}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("playlistId") Long playlistId) {

        playlistService.delete(playlistId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("users/{userId}/playlists")
    public ResponseEntity<CommonResponse> findByUserId(@PathVariable("userId") Long userId) {

        return new ResponseEntity<>(new CommonResponse(playlistService.findByUserId(userId)), HttpStatus.OK);
    }

    @GetMapping("playlists/me")
    public ResponseEntity<CommonResponse> findByMe() {

        Long userId = SecurityUtil.getCurrentUserId();

        return new ResponseEntity<>(new CommonResponse(playlistService.findByUserId(userId)), HttpStatus.OK);
    }

    @PostMapping("playlists/report")
    public ResponseEntity report(@RequestBody @Valid PlaylistReportRequest request) {
        playlistService.report(request);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("playlists/wards/me")
    public ResponseEntity<CommonResponse> findWardingPlaylist(
            PageMainRequest pageMainRequest,
            @RequestParam(value = "provider") String provider) {

        Page<PlaylistMainResponse> page = playlistService.findWardingPlaylist(pageMainRequest.toPageRequest(), provider);

        return new ResponseEntity<>(new PageResponse(page.getContent(), new PageInfoResponse(page)), HttpStatus.OK);
    }
}