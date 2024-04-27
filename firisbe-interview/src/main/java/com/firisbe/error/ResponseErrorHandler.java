package com.firisbe.error;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ResponseErrorHandler extends ResponseEntityExceptionHandler {

	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest req) {
		ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(), 
				"Total errors: "+ex.getErrorCount()+" and first error is: "+ex.getFieldError().getDefaultMessage(), req.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler(Exception.class) // exception handled
//	public ResponseEntity<ErrorDetails> handleExceptions(Exception ex) {
//
//		HttpStatus status = HttpStatus.CONFLICT;  
//
//		// converting the stack trace to String
//		String stackTrace = convertPrintStackToString(ex);
//
//		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), stackTrace), status);
//	}

	
	@ExceptionHandler(RecordsNotBeingFetchedException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleRecordsNotBeingFetchedException(RecordsNotBeingFetchedException ex) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;  

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(),  ex.getErrorDetail()), status);
	}
	
	@ExceptionHandler(DataInsertionConftlictException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleDataInsertionConftlictExceptionn(DataInsertionConftlictException ex) {

		HttpStatus status = HttpStatus.CONFLICT;  

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(),  ex.getErrorDetail()), status);
	}
	
	@ExceptionHandler(RecordCouldNotBeSavedException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleRecordCouldNotBeSavedException(RecordCouldNotBeSavedException ex) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;  

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), ex.getErrorDetail()), status);
	}
	
	private String convertPrintStackToString(Exception ex) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		String stackTrace = stringWriter.toString();
		return stackTrace;
	}
}
