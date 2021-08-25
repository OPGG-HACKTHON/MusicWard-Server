package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.dto.comment.request.CommentRequest;
import io.github.opgg.music_ward_server.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity postComment(@RequestBody CommentRequest request) {
        commentService.postComment(request);
        return new ResponseEntity(HttpStatus.OK);
    }

}
