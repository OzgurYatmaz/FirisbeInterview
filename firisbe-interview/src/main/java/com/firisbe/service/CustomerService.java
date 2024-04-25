package com.firisbe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.firisbe.model.Card;
import com.firisbe.model.Customer;
import com.firisbe.repository.CardRepository;
import com.firisbe.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CardRepository cardRepository;

	public List<Customer> getAllCustomers() {
		try {
			List<Customer> customers = customerRepository.findAll();
			return customers;
		} catch (Exception e) {
			LOGGER.error("Error occurred while retrieving customers", e);
			throw e;
		}


	}

	public void addCustomer(Customer customer) throws Exception {

		// checks if email provided is already used for another customer
		if (!customerRepository.existsByEmail(customer.getEmail())) {

			// if no card is specified with request body a default card will be assigned to
			// customer
			if (CollectionUtils.isEmpty(customer.getCards())) {
				assignADefaultCard(customer);
			}

			try {
				checkCardsAndCustomerNumber(customer.getCards(), customer.getCustomerNumber());
				assignCardNumbersFromCustomerNumber(customer, customer.getCustomerNumber());
			} catch (Exception e) {
				LOGGER.error("Error occurred while checking if card number is already used, detail: " + e.getMessage(),
						e);
				throw e;
			}
			customerRepository.save(customer);
		} else {
			throw new Exception("Email provided is already exist");
		}
	}

	private void assignADefaultCard(Customer customer) {

		List<Card> cards = new ArrayList<>();
		Card defaultCard = new Card();
		/*
		 * This is a dummy sample
		 */
		defaultCard.setCardNumber(UUID.randomUUID().toString());// cards.add(defaultCard);
		defaultCard.setCustomerNumber(customer.getCustomerNumber());// cards.add(defaultCard);
		cards.add(defaultCard);
		customer.setCards(cards);

	}

	private void checkCardsAndCustomerNumber(List<Card> cards, String customerNumber) throws Exception {
		boolean areThereAnyDupliceteCardNumbers = false;
		
		if (customerRepository.existsByCustomerNumber(customerNumber)) {
			throw new Exception("Customer number " + customerNumber + " are already  used! ");
		}
	
		StringBuilder nonUniquecardNumbers = new StringBuilder();
		for (Card card : cards) {
			if (cardRepository.existsByCardNumber(card.getCardNumber())) {
				nonUniquecardNumbers.append(card.getCardNumber()).append(", ");
				areThereAnyDupliceteCardNumbers = true;
			}
		}
		if (areThereAnyDupliceteCardNumbers) {
			throw new Exception("Following card numbers are already  used: " + nonUniquecardNumbers.toString());
		}

	}


	private void assignCardNumbersFromCustomerNumber(Customer customer, String customerNumber) {
		List<Card> updatedCards = new ArrayList<>();
		List<Card> cards = customer.getCards();
		if(!CollectionUtils.isEmpty(cards)) {
			for (Card card : cards) {
				card.setCustomerNumber(customerNumber);
				updatedCards.add(card);
			}
			
			customer.setCards(updatedCards);
			
		}
	}

}
