package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class EmailTooLongException extends MusicWardException {

    public EmailTooLongException() {
        super(ErrorCode.EMAIL_TOO_LONG);
    }

}
