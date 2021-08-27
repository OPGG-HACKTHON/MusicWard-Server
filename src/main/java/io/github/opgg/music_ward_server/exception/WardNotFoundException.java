package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class WardNotFoundException extends MusicWardException {

    public WardNotFoundException() {
        super(ErrorCode.WARD_NOT_FOUND);
    }

}
