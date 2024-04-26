package com.firisbe.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class RecordCouldNotBeSavedException extends RuntimeException {

	public RecordCouldNotBeSavedException(String message) {
		super(message);
	}

}
