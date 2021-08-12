package io.github.opgg.music_ward_server.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ErrorCode {

    USER_NOT_FOUND(404, "User not found.");

    private final int status;
    private final String message;

}
