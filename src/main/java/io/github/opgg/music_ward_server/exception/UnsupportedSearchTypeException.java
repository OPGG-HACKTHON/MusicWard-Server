package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class UnsupportedSearchTypeException extends MusicWardException {

    public UnsupportedSearchTypeException() {
        super(ErrorCode.UNSUPPORTED_SEARCH_TYPE);
    }
}