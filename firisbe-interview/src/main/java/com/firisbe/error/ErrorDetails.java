package com.firisbe.error;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error Model Information")
public class ErrorDetails {

	@Schema(description = "Time when error occured", example = "2024-04-27T11:59:30.2927549")
	private LocalDateTime timestamp;
	@Schema(description = "Error message")
	private String message;
	@Schema(description = "Further details about the error")
	private String details;

	public ErrorDetails(LocalDateTime timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
	

}