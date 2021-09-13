package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class MatchNotFoundException extends MusicWardException {
    public MatchNotFoundException() {
        super(ErrorCode.MATCH_NOT_FOUND);
    }
}
