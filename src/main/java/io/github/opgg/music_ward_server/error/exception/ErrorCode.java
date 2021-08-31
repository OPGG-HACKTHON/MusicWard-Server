package io.github.opgg.music_ward_server.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "Invalid input value."),
    PLAYLIST_NOT_FOUND(400, "Playlist not found."),
    EMAIL_TOO_LONG(400, "Email too long."),
    REQUEST_FAIL_TO_OTHER_SERVER(400, "Request fail to other server."),
    UNSUPPORTED_PROVIDER(400, "Unsupported provider."),
    UNSUPPORTED_RANKING_TYPE(400, "Unsupported ranking type."),
    UNSUPPORTED_SEARCH_TYPE(400, "Unsupported search type."),
    ALREADY_WARDED_PLAYLIST(400, "Already warded playlist."),
    INVALID_TOKEN(401, "Invalid token."),
    EXPIRED_ACCESS_TOKEN(401, "Expired access token."),
    EXPIRED_REFRESH_TOKEN(401, "Expired refresh token."),
    CREDENTIALS_NOT_FOUND(401, "Credentials not found."),
    EMPTY_REFRESH_TOKEN(401, "Empty refresh token."),
    NOT_YOUR_COMMENT(403, "Not your comment."),
    USER_NOT_FOUND(404, "User not found."),
    CHAMPION_NOT_FOUND(404, "Champion not found."),
    COMMENT_NOT_FOUND(404, "Comment not found."),
    WARD_NOT_FOUND(404, "Ward not found.");

    private final int status;
    private final String message;

}
