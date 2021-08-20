package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class RequestFailToOtherServerException extends MusicWardException {

    public RequestFailToOtherServerException() {
        super(ErrorCode.REQUEST_FAIL_TO_OTHER_SERVER);
    }

}
