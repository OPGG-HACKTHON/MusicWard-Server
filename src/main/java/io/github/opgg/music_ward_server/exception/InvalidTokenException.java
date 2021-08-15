package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class InvalidTokenException extends MusicWardException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}
