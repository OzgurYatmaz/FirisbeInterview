package com.firisbe.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.firisbe.model.Customer;
import com.firisbe.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Customer controller", description = "Add  and fetch customers")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	public CustomerService customerService;

	@Operation(summary = "Add customer", description = "Adds  new customer to our database")
	@PostMapping("/add-customer")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer) {

		Customer addedCustomer = null;
		try {
			addedCustomer = customerService.addCustomer(customer);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to add customer. Error detail: " + e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("User with id " + addedCustomer.getId() + " is created: ");
	}

	@Operation(summary = "Fetch all customers", description = "Fetches all customers exist in our database")
	@GetMapping("/get-all-customers")
	public List<Customer> getAllCustomers() {

		List<Customer> allCustomers = customerService.getAllCustomers();

		return CollectionUtils.isEmpty(allCustomers) ? null : allCustomers;

	}

}
