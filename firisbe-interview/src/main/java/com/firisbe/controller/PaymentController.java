package com.firisbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firisbe.model.PaymentRequestDTO;
import com.firisbe.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payment controller", description = "Make  and monitor payments")
@RestController
@RequestMapping("/transactions")
public class PaymentController {

	@Autowired
	public PaymentService paymentService;

	@Operation(summary = "Make Payment", description = "Sends to payment request to payment service and records it to DB")
	@PostMapping("/make-payment")
	public String makePayment(@RequestBody PaymentRequestDTO paymentRequest) {
		try {
			paymentService.processPayment(paymentRequest);
			return "Payment is made";

		} catch (Exception e) {
			return "Pament couldnot been made, error: " + e.getMessage();
		}
	}

}
