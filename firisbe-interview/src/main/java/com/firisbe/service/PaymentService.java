package com.firisbe.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private PaymentServiceConfig configParameters;

	@Transactional
	public void processPayment(PaymentRequestDTO paymentRequest) throws Exception {
		// Fetch the card by cardNumber
		if (!cardRepository.existsByCardNumber(paymentRequest.getCardNumber())) {
			throw new Exception("Card not found");
		}

		Card card = cardRepository.findByCardNumber(paymentRequest.getCardNumber()).get(0);
		
		// create an object for external service's request body. This is just dummy
		// example
		Payment payment = prepareExternalRequest(paymentRequest, card);

		// Call the external payment service
		try {
			ResponseEntity<String> responseEntity = sendPaymentRequestToExternalService(paymentRequest);

			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				// only successful payments are saved to DB for now.
				// Save the payment
				paymentRepository.save(payment);
				// You can also update other information like card balance here if needed
				System.out.println("Payment processed successfully");
			} else {
				throw new Exception("Response Code: " + responseEntity.getStatusCode() + ", Payment failed!");
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while sending payment request to exteranal payment service: ", e);
			throw new Exception("An error occured while accessing external paymnet service. Detail: " + e.getMessage());
		}

	}

	private Payment prepareExternalRequest(PaymentRequestDTO paymentRequest, Card card) {
		Payment payment = new Payment();
		payment.setCardNumber(card.getCardNumber());
		payment.setAmount(paymentRequest.getAmount());
		LocalDateTime paymentTime = LocalDateTime.now();
		payment.setPaymentDate(paymentTime);
		payment.setCustomerNumber(card.getCustomerNumber());
		return payment;
	}

	private ResponseEntity<String> sendPaymentRequestToExternalService(PaymentRequestDTO externalRequest) {
		// Here you would send the payment request to an external service
		// This is just a dummy example

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForEntity(configParameters.getPaymentserviceurl(), externalRequest, String.class);
	}

	public List<Payment> findPaymentsBySearchCriteria(String cardNumber, String customerNumber) throws Exception {

		 List<Payment> payments;
		try {
			payments = paymentRepository.findByCardNumberOrCustomerNumber(customerNumber, cardNumber);
			return payments;
		} catch (Exception e) {
			LOGGER.error("Error occurred while fetching payment records from DB: ", e);
			throw new Exception("Error occured while fetching payment records from DB; detail: "+e.getMessage());
		} 
	}

}
