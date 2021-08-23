package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class PlaylistNotFoundException extends MusicWardException {

    public PlaylistNotFoundException() {
        super(ErrorCode.PLAYLIST_NOT_FOUND);
    }
}
