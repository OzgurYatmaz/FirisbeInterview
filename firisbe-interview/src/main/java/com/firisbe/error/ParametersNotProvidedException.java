package com.firisbe.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ParametersNotProvidedException extends RuntimeException {

	private String errorDetail;

	public ParametersNotProvidedException(String message, String errorDetail) {
		super(message);
		this.errorDetail = errorDetail;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
	
	

}
