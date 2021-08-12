package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class UserNotFoundException extends MusicWardException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

}
