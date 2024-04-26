package com.firisbe.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class RecordsNotBeingFetchedException extends RuntimeException {

	public RecordsNotBeingFetchedException(String message) {
		super(message);
	}

}
