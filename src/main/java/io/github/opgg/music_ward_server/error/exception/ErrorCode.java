package io.github.opgg.music_ward_server.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ErrorCode {

    INVALID_TOKEN(401, "Invalid token."),
    EXPIRED_ACCESS_TOKEN(401, "Expired access token."),
    EXPIRED_REFRESH_TOKEN(401, "Expired refresh token."),
    USER_NOT_FOUND(404, "User not found.");

    private final int status;
    private final String message;

}
