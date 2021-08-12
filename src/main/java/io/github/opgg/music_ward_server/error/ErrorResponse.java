package io.github.opgg.music_ward_server.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String message;

}
