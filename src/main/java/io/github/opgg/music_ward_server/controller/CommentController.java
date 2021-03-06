package io.github.opgg.music_ward_server.controller;

import javax.validation.Valid;

import io.github.opgg.music_ward_server.dto.comment.request.CommentRequest;
import io.github.opgg.music_ward_server.dto.comment.request.EditCommentRequest;
import io.github.opgg.music_ward_server.dto.comment.request.RemoveCommentRequest;
import io.github.opgg.music_ward_server.service.comment.CommentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity postComment(@RequestBody @Valid CommentRequest request) {
        commentService.postComment(request);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping("/comment")
    public ResponseEntity editComment(@RequestBody @Valid EditCommentRequest request) {
        commentService.editComment(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/comment")
    public ResponseEntity removeComment(@RequestBody RemoveCommentRequest request) {
        commentService.removeComment(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
