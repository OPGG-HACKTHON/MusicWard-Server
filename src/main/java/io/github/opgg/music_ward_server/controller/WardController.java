package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.dto.ward.request.PostWardRequest;
import io.github.opgg.music_ward_server.dto.ward.request.RemoveWardRequest;
import io.github.opgg.music_ward_server.service.ward.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class WardController {

    private final WardService wardService;

    @PostMapping("/ward")
    public ResponseEntity postWard(@RequestBody PostWardRequest request) {
        wardService.postWard(request);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/ward")
    public ResponseEntity removeWard(@RequestBody RemoveWardRequest request) {
        wardService.removeWard(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
