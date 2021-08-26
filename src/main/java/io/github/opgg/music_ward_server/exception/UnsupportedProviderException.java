package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class UnsupportedProviderException extends MusicWardException {

    public UnsupportedProviderException() {
        super(ErrorCode.UNSUPPORTED_PROVIDER);
    }
}
