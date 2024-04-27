package com.firisbe.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

@Tag(name = "Payment controller", description = "Make  and query payments") // For Swagger
@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	public PaymentService paymentService;

	@Operation(summary = "Make Payment", description = "Sends to payment request to payment service and records it to DB")
	@PostMapping("/make-payment")
	public ResponseEntity<String> makePayment(@Valid @RequestBody PaymentRequestDTO paymentRequest) throws Exception {
		try {
			paymentService.processPayment(paymentRequest);
			return ResponseEntity.status(HttpStatus.OK).body("Payment is made ");

		} catch (Exception e) {
			throw e;
		}

	}

	@Operation(summary = "Fetch Payments", description = "Fethc payments made with parameters card number or customer number")
	@GetMapping("/fetch-payments")
	public ResponseEntity<Object> getPaymentsBySearchCriteria(@RequestParam(required = false) String cardNumber,
			@RequestParam(required = false) String customerNumber) throws Exception {
		try {
			List<Payment> payments = paymentService.findPaymentsBySearchCriteria(cardNumber, customerNumber);
			return ResponseEntity.ok(payments);

		} catch (Exception e) {
			throw e;
		}
	}

}
