package io.github.opgg.music_ward_server.service.comment;

import io.github.opgg.music_ward_server.dto.comment.request.CommentRequest;
import io.github.opgg.music_ward_server.dto.comment.request.EditCommentRequest;
import io.github.opgg.music_ward_server.dto.comment.request.RemoveCommentRequest;

public interface CommentService {
    void postComment(CommentRequest request);
    void editComment(EditCommentRequest request);
    void removeComment(RemoveCommentRequest request);
}
