package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class NotYourPlaylistException extends MusicWardException {

    public NotYourPlaylistException() {
        super(ErrorCode.NOT_YOUR_PLAYLIST);
    }
}