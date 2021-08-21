package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class ChampionNotFoundException extends MusicWardException {
    public ChampionNotFoundException() {
        super(ErrorCode.CHAMPION_NOT_FOUND);
    }
}
