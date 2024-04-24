package com.firisbe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.firisbe.model.Card;
import com.firisbe.model.Customer;
import com.firisbe.repository.CardRepository;
import com.firisbe.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CardRepository cardRepository;

	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();

		if (CollectionUtils.isEmpty(customers)) {
			return null;
		} else {
			return customers;
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
				checkIfAnyCardNumberIsAlreadyUsed(customer.getCards());
			} catch (Exception e) {
				throw e;
			}

			customerRepository.save(customer);
		} else {
			throw new Exception("Email provided is already exist");
		}
	}

	private void checkIfAnyCardNumberIsAlreadyUsed(List<Card> cards) throws Exception {
		boolean areThereAnyDuplicetes = false;
		StringBuilder nonUniquecardNumbers = new StringBuilder();
		for (Card card : cards) {
			if (cardRepository.existsByCardNumber(card.getCardNumber())) {
				nonUniquecardNumbers.append(card.getCardNumber()).append(", ");
				areThereAnyDuplicetes = true;
			}
		}
		if (areThereAnyDuplicetes) {
			throw new Exception("Following card numbers are already  used: " + nonUniquecardNumbers.toString());
		}

	}

	private void assignADefaultCard(Customer customer) {

		List<Card> cards = new ArrayList<>();
		Card defaultCard = new Card();
		/*
		 * This is a dummy sample
		 */
		defaultCard.setCardNumber(UUID.randomUUID().toString());// cards.add(defaultCard);
		customer.setCards(cards);

	}

}
