package com.firisbe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.firisbe.model.Card;
import com.firisbe.model.Customer;
import com.firisbe.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();

		if (CollectionUtils.isEmpty(customers)) {
			return null;
		} else {
			return customers;
		}

	}

	public void addCustomer(Customer customer) throws Exception {

		List<Customer> fetchCustomer = customerRepository.findByEmail(customer.getEmail());
		// checks if email provided is already used for another customer
		if (CollectionUtils.isEmpty(fetchCustomer)) {

			if (CollectionUtils.isEmpty(customer.getCards())) {
				assignADefaultCard(customer);
			}
			System.out.println("Saved Customer is: "+customer.toString());
			System.out.println("Customer Cards: "+customer.getCards());
			System.out.println("Customers first card number: "+customer.getCards().get(0).getCardNumber());
			System.out.println("------------------------------------------");
			System.out.println("Customer id: "+customer.getId());
			System.out.println("Card id for first card: "+customer.getCards().get(0).getId());
			
			customerRepository.save(customer);
		} else {
			throw new Exception("Email provided is already exist");
		}
	}

	private void assignADefaultCard(Customer customer) {

		List<Card> cards = new ArrayList<>();
		Card defaultCard = new Card();
		defaultCard.setCustomer(customer);
		cards.add(defaultCard);
		customer.setCards(cards);

	}

}
