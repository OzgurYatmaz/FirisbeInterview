package com.firisbe.controller;

import java.time.LocalDate;
import java.util.List;
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

import com.firisbe.error.ErrorDetails;
import com.firisbe.model.Payment;
import com.firisbe.model.PaymentRequestDTO;
import com.firisbe.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Payment controller", description = "Make  and query payments") // For Swagger
@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	public PaymentService paymentService;

	@Operation(summary = "Make Payment", description = "Sends payment request to external payment service and records the paymet details")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "When payment is made succesfully"),
		@ApiResponse(responseCode = "500", description = "When external payment service provider retuning diffrent code implying unsucessfull payment", content = { @Content(schema = @Schema(implementation = ErrorDetails.class)) }),
		@ApiResponse(responseCode = "502", description = "When external payment service provider fail in completing the payment", content = { @Content(schema = @Schema(implementation = ErrorDetails.class)) })
	  })
	@PostMapping("/make-payment")
	public ResponseEntity<String> makePayment(@Valid @RequestBody PaymentRequestDTO paymentRequest) throws Exception {
		try {
			paymentService.processPayment(paymentRequest);
			return ResponseEntity.status(HttpStatus.OK).body("Payment is made ");

		} catch (Exception e) {
			throw e;
		}

	}

	@Operation(summary = "Fetch by customer or card number", description = "Fethc payments made with parameters card number or customer number")
	@GetMapping("/fetch-payments")
	public ResponseEntity<List<Payment>> getPaymentsBySearchCriteria(@RequestParam(required = false) String cardNumber,
			@RequestParam(required = false) String customerNumber) throws Exception {
		try {
			List<Payment> payments = paymentService.findPaymentsBySearchCriteria(cardNumber, customerNumber);
			return ResponseEntity.ok(payments);

		} catch (Exception e) {
			throw e;
		}
	}

	@Operation(summary = "Fetch by date interval", description = "Fethc payments made with parameters start date and end date")
	@GetMapping("/fetch-payments-bydate")
    public ResponseEntity<List<Payment>> getAllPaymentsbyDateInterval(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    					@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws Exception {
		try {
			List<Payment> payments = paymentService.getAllPaymentsbyDateInterval(startDate, endDate);
			return ResponseEntity.ok(payments);

		} catch (Exception e) {
			throw e;
		}
    }
}
