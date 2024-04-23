package com.firisbe.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import com.firisbe.configuration.PaymentServiceConfig;
import com.firisbe.model.Card;
import com.firisbe.model.Payment;
import com.firisbe.model.PaymentRequestDTO;
import com.firisbe.repository.CardRepository;
import com.firisbe.repository.PaymentRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private PaymentServiceConfig configParameters;

	@Transactional
	public void processPayment(@Valid PaymentRequestDTO paymentRequest) throws Exception {
		// Fetch the card by cardNumber
		Card card = cardRepository.findByCardNumber(paymentRequest.getCardNumber()).get(0);
		if (ObjectUtils.isEmpty(card)) {
			throw new Exception("Card not found");
		}

		// create an object for external service's request body. This is just dummy example
		Payment payment = prepareExternalRequest(paymentRequest, card);

		// Call the external payment service
		try {
			ResponseEntity<String> responseEntity = sendPaymentRequestToExternalService(paymentRequest);

			// additional checks for payment result will be needed such as specific codes
			// and messages of the response object
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				// I only record successful payments to DB for now.
				// Save the payment
				paymentRepository.save(payment);
				// You can also update other information like card balance here if needed
				System.out.println("Payment processed successfully");
			} else {
				// Payment failed, you can throw an exception or handle it based on the response
				throw new Exception("Response Code: " + responseEntity.getStatusCode() + ", Payment failed!");
			}
		} catch (Exception e) {
			throw new Exception("An error occured while accessing external paymnet service. Detail: " + e.getMessage());
		}

	}

	private Payment prepareExternalRequest(PaymentRequestDTO paymentRequest, Card card) {
		Payment payment = new Payment();
		payment.setCard(card);
		payment.setAmount(paymentRequest.getAmount());
		payment.setPaymentDate(new Date());
		return payment;
	}

	private ResponseEntity<String> sendPaymentRequestToExternalService(PaymentRequestDTO externalRequest) {
		// Here you would send the payment request to an external service
		// This is just a dummy example

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForEntity(configParameters.getPaymentserviceurl(), externalRequest, String.class);
	}

	public void checkConfigParams(Payment payment) throws Exception {

		System.out.println(configParameters.getPaymentserviceurl());
		System.out.println(configParameters.getClientsecret());
	}

}
