package com.firisbe.controller;

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

import com.firisbe.error.ErrorDetails;
import com.firisbe.model.Customer;
import com.firisbe.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@Tag(name = "Customer controller", description = "Add  and fetch customers")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	public CustomerService customerService;

	@Operation(summary = "Add customer", description = "Adds  new customer to our database")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "When customer is successfully saved to data base"),
		@ApiResponse(responseCode = "409", description = "When submitted data is in conflict with existing data in database or with itself", content = { @Content(schema = @Schema(implementation = ErrorDetails.class)) })
	  })
	@PostMapping("/add-customer")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer) throws Exception {

		Customer addedCustomer = null;
		
		try {
			addedCustomer = customerService.addCustomer(customer);

		} catch (Exception e) {
			throw e;
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("User with id " + addedCustomer.getId() + " is created: ");
	}


	@Operation(summary = "Fetch all customers", description = "Fetches all customers exist in our database")
	@ApiResponses({
		    @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Customer.class)), mediaType = "application/json") }),
		    @ApiResponse(responseCode = "500", description = "When error being occured during querying database", content = { @Content(schema = @Schema(implementation = ErrorDetails.class)) })
		  })
	@GetMapping("/get-all-customers")
	public List<Customer> getAllCustomers() throws Exception {

		List<Customer> allCustomers = null;

		try {
			allCustomers = customerService.getAllCustomers();
		} catch (Exception e) {
			throw e;
		}

		return CollectionUtils.isEmpty(allCustomers) ? null : allCustomers;

	}

}
