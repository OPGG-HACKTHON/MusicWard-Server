package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class EmptyRefreshTokenException extends MusicWardException {

    public EmptyRefreshTokenException() {
        super(ErrorCode.EMPTY_REFRESH_TOKEN);
    }
}
