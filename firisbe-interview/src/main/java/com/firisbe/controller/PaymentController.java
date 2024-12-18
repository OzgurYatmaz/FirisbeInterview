/**
 * This package contains classes for all controllers of the project
 */
package com.firisbe.controller;

import com.firisbe.filter.PaymentFilter;
import com.firisbe.service.PaymentService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.firisbe.dto.PaymentDTO;
import com.firisbe.dto.PaymentRequestDTO;
import com.firisbe.error.ErrorDetails;
import com.firisbe.error.RecordsNotBeingFetchedException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Main payment controller to make payment from registered card balance and
 * query payments
 * 
 * @throws Various exceptions explaining the reasons of failures.
 * 
 * @see com.firisbe.error.ResponseErrorHandler class to see possible errors
 *      might be thrown from here
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */

@Tag(name = "Payment controller", description = "Make  and query payments") // For Swagger
@RestController
@RequestMapping("/payments")
public class PaymentController {

	/**
	 * Payment related operations will be done with this
	 */
	@Autowired
	public PaymentService paymentService;

	/**
	 * 
	 * Make payment from registered card in data base.
	 * 
	 * @param paymentRequest object includes card number to associate payment to
	 *                       card and payment amount.
	 * @return Payment response object includes info if payment is successful or
	 *         failed.
	 * @throws various exception to explaining why payment is failed.
	 * 
	 * @see com.firisbe.error.ResponseErrorHandler class to see possible errors
	 *      might be thrown from here
	 * 
	 */
	@Operation(summary = "Make Payment from balance of the card", description = "Sends payment request to external payment service and records the paymet details")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "When payment is made succesfully"),
			@ApiResponse(responseCode = "500", description = "When external payment service provider retuning diffrent code implying unsucessfull payment", content = {
					@Content(schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(schema = @Schema(hidden = true)) }),
			@ApiResponse(responseCode = "406", description = "When card balance is insufficient to make the payment", content = {
					@Content(schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "502", description = "When external payment service provider fail in completing the payment", content = {
					@Content(schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PostMapping("/make-payment")
	public ResponseEntity<String> makePayment(@Valid @RequestBody PaymentRequestDTO paymentRequest) throws Exception {
		try {
			paymentService.processPayment(paymentRequest);
			return ResponseEntity.status(HttpStatus.OK).body("Payment is made ");

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 
	 * To query payments from database with two optional parameters
	 * 
	 * @param filter
	 * @param pageable
	 * @return list of Payment objects
	 * @throws RecordsNotBeingFetchedException exception with message explaining the
	 *                                         error detail.
	 * 
	 * 
	 */
	@Operation(summary = "Fetch by customer or card number", description = "Fethc payments made with parameters card number or customer number")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = PaymentDTO.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "When error being occured during querying database", content = {
					@Content(schema = @Schema(implementation = ErrorDetails.class)) }) })
	@GetMapping("/fetch-payments")
	public Page<PaymentDTO> getPaymentsBySearchCriteria(PaymentFilter filter, Pageable pageable) throws Exception {
		try {
			return paymentService.findPaymentsBySearchCriteria(filter, pageable);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * To query payments from database with two compulsory parameters defining the date interval
	 * 
	 * @param startDate format: YYYY-MM-DD example: 2024-04-27
	 * @param endDate  format: YYYY-MM-DD example: 2024-04-28
	 * @return list of Payment objects
	 * @throws RecordsNotBeingFetchedException exception with message explaining the
	 *                                         error detail.
	 * 
	 * 
	 */
	@Operation(summary = "Fetch by date interval", description = "Fethc payments made with parameters start date and end date")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = PaymentDTO.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "When error being occured during querying database", content = {
					@Content(schema = @Schema(implementation = ErrorDetails.class)) }) })
	@GetMapping("/fetch-payments-bydate")
	public Page<PaymentDTO> getAllPaymentsbyDateInterval(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, Pageable pageable)
			throws Exception {
		try {
			return paymentService.getAllPaymentsbyDateInterval(startDate, endDate, pageable);
		} catch (Exception e) {
			throw e;
		}
	}
}
