/**
 * This package contains classes for handling the exceptions thrown from controllers.
 * .
 */
package com.firisbe.error;

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

/**
 *  This  class is for handling the exceptions thrown from controllers. I guess method names are self explanatory :)
 * 
 * 
 * @see com.firisbe.controller.CustomerController  
 * @see com.firisbe.controller.PaymentController  
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-07
 * 
 */

@ControllerAdvice
public class ResponseErrorHandler extends ResponseEntityExceptionHandler {

	/**
	 * 
	 * for managing the validation errors auto controlled by spring validation annotations
	 * 
	 */	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest req) {
		ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(), 
				"Total errors: "+ex.getErrorCount()+" and first error is: "+ex.getFieldError().getDefaultMessage(), req.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, status);
	}

	
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
	
	
	@ExceptionHandler(RecordsNotExistException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleRecordCouldNotBeSavedException(RecordsNotExistException ex) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), ex.getErrorDetail()), status);
	}
 
	
	@ExceptionHandler(PaymentServiceProviderException.class) // exception handled
	public ResponseEntity<ErrorDetails> handlePaymentServiceProviderException(PaymentServiceProviderException ex) {

		HttpStatus status = HttpStatus.BAD_GATEWAY;

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), ex.getErrorDetail()), status);
	}
 
	
	@ExceptionHandler(ExternalServiceException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleExternalServiceException(ExternalServiceException ex) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), ex.getErrorDetail()), status);
	}
	
	
	
	@ExceptionHandler(InsufficientCardBalanceException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleExternalServiceException(InsufficientCardBalanceException ex) {

		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), ex.getErrorDetail()), status);
	}
	
	@ExceptionHandler(ParametersNotProvidedException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleParametersNotProvidedException(ParametersNotProvidedException ex) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), ex.getErrorDetail()), status);
	}
}
