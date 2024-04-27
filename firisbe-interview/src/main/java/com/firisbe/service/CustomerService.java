package com.firisbe.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.firisbe.error.DataInsertionConftlictException;
import com.firisbe.error.RecordCouldNotBeSavedException;
import com.firisbe.error.RecordsNotBeingFetchedException;
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

	public List<Customer> getAllCustomers() throws Exception {
		try {
			List<Customer> customers = customerRepository.findAll();
			return customers;
		} catch (Exception e) {
			LOGGER.error("Error occurred while retrieving customers", e);
			throw new RecordsNotBeingFetchedException("Error occurred while retrieving customers!", e.getMessage());
		}

	}

	public Customer addCustomer(Customer customer) throws DataInsertionConftlictException {

		// checks if email provided is already used for another customer
		Customer savedCustomer = null;
		if (!customerRepository.existsByEmail(customer.getEmail())) {

			// if no card is specified with request body a default card will be assigned to
			// customer
			if (CollectionUtils.isEmpty(customer.getCards())) {
				assignADefaultCard(customer);
			}

			try {
				checkCardsAndCustomerNumber(customer.getCards(), customer.getCustomerNumber());
				assignCardNumbersFromCustomerNumber(customer, customer.getCustomerNumber());
			} catch (DataInsertionConftlictException e) {
				LOGGER.error("Error occurred while checking if card number is already used, detail: " + e.getMessage(),
						e);
				throw new DataInsertionConftlictException("Conflict in provided data exception", e.getMessage());
			}
			try {
				savedCustomer = customerRepository.save(customer);
			} catch (Exception e) {
				LOGGER.error("Error occurred while saving the customer to data base, detail: " + e.getMessage(),
						e);
				throw new RecordCouldNotBeSavedException("Error occurred while saving the customer to DB", e.getMessage());
			}
		} else {
			throw new DataInsertionConftlictException("Conflict in provided data exception", "Email "+customer.getEmail()+" is already allocated");
		}
		
		return savedCustomer;
	}

	private void assignADefaultCard(Customer customer) {

		List<Card> cards = new ArrayList<>();
		Card defaultCard = new Card();
		defaultCard.setCardNumber(UUID.randomUUID().toString());// cards.add(defaultCard);
		defaultCard.setCustomerNumber(customer.getCustomerNumber());// cards.add(defaultCard);
		cards.add(defaultCard);
		customer.setCards(cards);

	}

	private void checkCardsAndCustomerNumber(List<Card> cards, String customerNumber) throws DataInsertionConftlictException {
		boolean areThereAnyDupliceteCardNumbers = false;

		if (customerRepository.existsByCustomerNumber(customerNumber)) {
			throw new DataInsertionConftlictException( "Customer number " + customerNumber + " is already allocated", "line 100");
		}
		List<String> dublicateChecker = new ArrayList<>();

		StringBuilder nonUniquecardNumbers = new StringBuilder();
		for (Card card : cards) {
			dublicateChecker.add(card.getCardNumber());
			if (cardRepository.existsByCardNumber(card.getCardNumber())) {
				nonUniquecardNumbers.append(card.getCardNumber()).append(", ");
				areThereAnyDupliceteCardNumbers = true;
			}
		}
		if (areThereAnyDupliceteCardNumbers) {
			throw new DataInsertionConftlictException("Following card numbers are already  used: "+ nonUniquecardNumbers.toString(),"line 113");
		}

		//checks if there are any duplicate card numbers in the list  of cards
		areThereDuclicateEntriesForCardNumbers(dublicateChecker);

	}

	//checks if there are any duplicate card numbers in the requestbody of add customer object
	private void areThereDuclicateEntriesForCardNumbers(List<String> dublicateChecker) throws DataInsertionConftlictException {
		List<String> duplicates = listDuplicateUsingFilterAndSetAdd(dublicateChecker);
		if (!CollectionUtils.isEmpty(duplicates)) {
			StringBuilder nonUniquecardNumbers = new StringBuilder();
			for (String cardNumber : duplicates) {
				nonUniquecardNumbers.append(cardNumber).append(", ");
			}
			throw new DataInsertionConftlictException("Following card numbers are duplicates "+ nonUniquecardNumbers.toString(),"Line 129");
		}
	}

	

	List<String> listDuplicateUsingFilterAndSetAdd(List<String> list) {
		Set<String> elements = new HashSet<>();
		return list.stream().filter(n -> !elements.add(n)).collect(Collectors.toList());
	}

	private void assignCardNumbersFromCustomerNumber(Customer customer, String customerNumber) {
		List<Card> updatedCards = new ArrayList<>();
		List<Card> cards = customer.getCards();
		if (!CollectionUtils.isEmpty(cards)) {
			for (Card card : cards) {
				card.setCustomerNumber(customerNumber);
				updatedCards.add(card);
			}

			customer.setCards(updatedCards);

		}
	}

}
