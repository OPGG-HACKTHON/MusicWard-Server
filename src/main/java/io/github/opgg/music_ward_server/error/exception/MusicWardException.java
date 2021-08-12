package io.github.opgg.music_ward_server.error.exception;

import lombok.Getter;

@Getter
public class MusicWardException extends RuntimeException {

    private ErrorCode errorCode;

    public MusicWardException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
