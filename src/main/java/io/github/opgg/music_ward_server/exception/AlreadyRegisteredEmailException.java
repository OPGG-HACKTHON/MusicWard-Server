package io.github.opgg.music_ward_server.exception;

import io.github.opgg.music_ward_server.error.exception.ErrorCode;
import io.github.opgg.music_ward_server.error.exception.MusicWardException;

public class AlreadyRegisteredEmailException extends MusicWardException {

	public AlreadyRegisteredEmailException() {
		super(ErrorCode.ALREADY_REGISTERED_EMAIL);
	}

}
