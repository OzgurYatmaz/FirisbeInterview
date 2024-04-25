package com.firisbe.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firisbe.model.Payment;
import com.firisbe.model.PaymentRequestDTO;
import com.firisbe.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Payment controller", description = "Make  and monitor payments") // For Swagger
@RestController
@RequestMapping("/transactions")
public class PaymentController {

	@Autowired
	public PaymentService paymentService;

	@Operation(summary = "Make Payment", description = "Sends to payment request to payment service and records it to DB")
	@PostMapping("/make-payment")
	public String makePayment(@Valid @RequestBody PaymentRequestDTO paymentRequest) {
		try {
			paymentService.processPayment(paymentRequest);
			return "Payment is made";

		} catch (Exception e) {
			return "Pament couldnot been made, error: " + e.getMessage();
		}
	}

	 

	@GetMapping("/payments")
	public ResponseEntity<List<Payment>> getPaymentsBySearchCriteria(@RequestParam(required = false) String cardNumber,
			@RequestParam(required = false) String customerNumber) {
		List<Payment> payments;
		if (cardNumber != null) {
			payments = paymentService.findPaymentsBySearchCriteria(cardNumber, null);
		} else if (customerNumber != null) {
			payments = paymentService.findPaymentsBySearchCriteria(null, customerNumber);
		} else {
			// Both cardNumber and customerNumber are null, return an empty list or handle
			payments = List.of();
		}
		return ResponseEntity.ok(payments);
	}

}
