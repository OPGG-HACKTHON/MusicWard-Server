package io.github.opgg.music_ward_server.error;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MusicWardException.class)
    public ResponseEntity<ErrorResponse> serverExceptionHandler(MusicWardException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(errorCode.getStatus(), errorCode.getMessage()),
                HttpStatus.valueOf(errorCode.getStatus()));
    }

}
