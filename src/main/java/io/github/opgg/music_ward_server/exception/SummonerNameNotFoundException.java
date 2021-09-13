package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class SummonerNameNotFoundException extends MusicWardException {
    public SummonerNameNotFoundException() {
        super(ErrorCode.SUMMONER_NAME_NOT_FOUND);
    }
}
