package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class UnsupportedSortTypeException extends MusicWardException {

    public UnsupportedSortTypeException() {
        super(ErrorCode.UNSUPPORTED_SORT_TYPE);
    }
}