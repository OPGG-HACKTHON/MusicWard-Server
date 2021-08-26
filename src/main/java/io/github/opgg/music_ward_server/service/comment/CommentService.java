package io.github.opgg.music_ward_server.service.comment;

import io.github.opgg.music_ward_server.dto.comment.request.CommentRequest;

public interface CommentService {
    void postComment(CommentRequest request);
}
