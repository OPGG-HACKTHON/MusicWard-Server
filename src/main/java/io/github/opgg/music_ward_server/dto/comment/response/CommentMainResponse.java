package io.github.opgg.music_ward_server.dto.comment.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.dto.user.response.UserMainResponse;
import io.github.opgg.music_ward_server.entity.comment.Comment;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentMainResponse {

    private final Long commentId;
    private final String content;
    private final UserMainResponse user;

    public CommentMainResponse(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.user = new UserMainResponse(comment.getUser());
    }
}