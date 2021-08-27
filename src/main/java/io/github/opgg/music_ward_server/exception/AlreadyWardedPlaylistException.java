package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class AlreadyWardedPlaylistException extends MusicWardException {

    public AlreadyWardedPlaylistException() {
        super(ErrorCode.ALREADY_WARDED_PLAYLIST);
    }

}
