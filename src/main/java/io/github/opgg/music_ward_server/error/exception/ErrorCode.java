package io.github.opgg.music_ward_server.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ErrorCode {

    EMAIL_TOO_LONG(400, "Email too long."),
    REQUEST_FAIL_TO_OTHER_SERVER(400, "Request fail to other server."),
    INVALID_TOKEN(401, "Invalid token."),
    EXPIRED_ACCESS_TOKEN(401, "Expired access token."),
    EXPIRED_REFRESH_TOKEN(401, "Expired refresh token."),
    USER_NOT_FOUND(404, "User not found."),
    CHAMPION_NOT_FOUND(404, "Champion not found."),
    CREDENTIALS_NOT_FOUND(401, "Credentials not found."),
    EMPTY_REFRESH_TOKEN(401, "Empty refresh token."),
    INVALID_INPUT_VALUE(400, "Invalid input value."),
    ENTITY_NOT_FOUND(400, "Entity not found.");

    private final int status;
    private final String message;

}
