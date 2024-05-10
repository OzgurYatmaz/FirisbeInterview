/**
 * This package contains classes for services
 */
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

import com.firisbe.dto.AddCustomerRequestDTO;
import com.firisbe.dto.CardDTO;
import com.firisbe.dto.CustomerDTO;
import com.firisbe.entity.Card;
import com.firisbe.entity.Customer;
import com.firisbe.error.DataInsertionConftlictException;
import com.firisbe.error.RecordCouldNotBeSavedException;
import com.firisbe.error.RecordsNotBeingFetchedException;
import com.firisbe.repository.CardRepository;
import com.firisbe.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main customer service's business logic to add to database and fetch customers
 * from database
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
@Service
public class CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

	/**
	 * Customer related database operations are done with this
	 */
	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Card related database operations are done with this
	 */
	@Autowired
	private CardRepository cardRepository;

	/**
	 * 
	 * Fetches all customers registered in database
	 * 
	 * @throws RecordsNotBeingFetchedException if records could not be fetched from
	 *                                         database.
	 * 
	 */
	public List<CustomerDTO> getAllCustomers() throws Exception {
		try {
			List<Customer> customersFetched = customerRepository.findAll();
			List<CustomerDTO> customers = convertCustomerEntityToDTO(customersFetched);
			return customers;
		} catch (Exception e) {
			LOGGER.error("Error occurred while retrieving customers", e);
			throw new RecordsNotBeingFetchedException("Error occurred while retrieving customers!", e.getMessage());
		}

	}

	/**
	 * 
	 * Entity object fetched from database is converted to DTO object for web service return.
	 * 
	 * @param entity object fetched from database.
	 * 
	 * @return DTO object for API return type.
	 * 
	 * 
	 */
	private List<CustomerDTO> convertCustomerEntityToDTO(List<Customer> customersFetched) {
		if (!CollectionUtils.isEmpty(customersFetched)) {
			List<CustomerDTO> customers = new ArrayList<CustomerDTO>();
			for (Customer c : customersFetched) {
				CustomerDTO tempCustomer = new CustomerDTO();
				tempCustomer.setCustomerNumber(c.getCustomerNumber());
				tempCustomer.setName(c.getName());
				tempCustomer.setEmail(c.getEmail());
				customers.add(tempCustomer);
			}

			return customers;

		}
		return null;
	}

	/**
	 * 
	 * Adds customer to database if customer object includes cards it will also adds
	 * cards to database if no cards provided it will associate one default card to
	 * customer.
	 * 
	 * @param Customer object with or without cards field.
	 * @throws DataInsertionConftlictException if provided data conflict with
	 *                                         existing data in database
	 * 
	 * 
	 */
	public Customer addCustomer(AddCustomerRequestDTO addCustomerRequest) throws DataInsertionConftlictException {

		Customer customer = convertDTO_To_Entity(addCustomerRequest);
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
				LOGGER.error("Error occurred while saving the customer to data base, detail: " + e.getMessage(), e);
				throw new RecordCouldNotBeSavedException("Error occurred while saving the customer to DB",
						e.getMessage());
			}
		} else {
			throw new DataInsertionConftlictException("Conflict in provided data exception",
					"Email " + customer.getEmail() + " is already allocated");
		}

		return savedCustomer;
	}

	/**
	 * 
	 * request dto object is being converted to entity object to be handled JPA.
	 * 
	 * @param request body object for transmitting data in web.
	 * 
	 * @return customer entity object to be saved database by JPA.
	 * 
	 * 
	 */
	private Customer convertDTO_To_Entity(AddCustomerRequestDTO addCustomerRequest) {

		Customer customer = new Customer();
		customer.setName(addCustomerRequest.getName());
		customer.setEmail(addCustomerRequest.getEmail());
		customer.setCustomerNumber(addCustomerRequest.getCustomerNumber());
		List<CardDTO> cards = addCustomerRequest.getCards();
		if (!CollectionUtils.isEmpty(cards)) {
			List<Card> newCards = new ArrayList<Card>();
			for (CardDTO card : cards) {
				Card tempCard = new Card();
				tempCard.setCardNumber(card.getCardNumber());
				tempCard.setCustomerNumber(addCustomerRequest.getCustomerNumber());
				tempCard.setBalance(card.getBalance());
				newCards.add(tempCard);
			}
			customer.setCards(newCards);
		}
		return customer;
	}

	/**
	 * 
	 * If customer object has no cards field it will creates default card for the
	 * customer.
	 * 
	 * @param Customer object with cards field empty.
	 * 
	 * 
	 */
	private void assignADefaultCard(Customer customer) {

		List<Card> cards = new ArrayList<>();
		Card defaultCard = new Card();
		defaultCard.setBalance(1000.00);
		defaultCard.setCardNumber(UUID.randomUUID().toString());
		defaultCard.setCustomerNumber(customer.getCustomerNumber());
		cards.add(defaultCard);
		customer.setCards(cards);

	}

	/**
	 * 
	 * Checks if customer number already allocated for another customer in database
	 * or is there any duplicate card number in provided data.
	 * 
	 * @param List     of card objects
	 * @param Customer number
	 *
	 * @throws DataInsertionConftlictException if provided data is already exist in
	 *                                         database or .
	 * 
	 * 
	 */
	private void checkCardsAndCustomerNumber(List<Card> cards, String customerNumber)
			throws DataInsertionConftlictException {
		boolean areThereAnyDupliceteCardNumbers = false;

		if (customerRepository.existsByCustomerNumber(customerNumber)) {
			throw new DataInsertionConftlictException("Customer number " + customerNumber + " is already allocated",
					"");
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
			throw new DataInsertionConftlictException(
					"Following card numbers are already  used: " + nonUniquecardNumbers.toString(), "");
		}

		// checks if there are any duplicate card numbers in the list of cards
		areThereDuclicateEntriesForCardNumbers(dublicateChecker);

	}

	/**
	 * 
	 * Checks if is there any duplicate card numbers in provided list of card
	 * objects
	 * 
	 * @param List of strings containing the card numbers provided
	 *
	 * @throws DataInsertionConftlictException if provided card numbers contains any
	 *                                         duplicates
	 * 
	 * 
	 */
	private void areThereDuclicateEntriesForCardNumbers(List<String> dublicateChecker)
			throws DataInsertionConftlictException {
		List<String> duplicates = listDuplicateUsingFilterAndSetAdd(dublicateChecker);
		if (!CollectionUtils.isEmpty(duplicates)) {
			StringBuilder nonUniquecardNumbers = new StringBuilder();
			for (String cardNumber : duplicates) {
				nonUniquecardNumbers.append(cardNumber).append(", ");
			}
			throw new DataInsertionConftlictException(
					"Following card numbers are duplicates " + nonUniquecardNumbers.toString(), "");
		}
	}

	/**
	 * 
	 * Finds all duplicated card numbers if any.
	 * 
	 * @param List of strings containing the card numbers provided
	 * @return List of string containing the duplicate elements
	 * @throws DataInsertionConftlictException if provided card numbers contains any
	 *                                         duplicates
	 * 
	 * 
	 */
	private List<String> listDuplicateUsingFilterAndSetAdd(List<String> list) {
		Set<String> elements = new HashSet<>();
		return list.stream().filter(n -> !elements.add(n)).collect(Collectors.toList());
	}

	/**
	 * 
	 * Assigns the customer number to all cards provided in cards field of the
	 * customer object
	 * 
	 * @param Customer object
	 * @param Customer number (it is already exist inside first parameter but I
	 *                 think it is more human readable like that)
	 * 
	 * 
	 */
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
