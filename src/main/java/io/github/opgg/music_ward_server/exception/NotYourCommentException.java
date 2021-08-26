package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class NotYourCommentException extends MusicWardException {

    public NotYourCommentException() {
        super(ErrorCode.NOT_YOUR_COMMENT);
    }

}
