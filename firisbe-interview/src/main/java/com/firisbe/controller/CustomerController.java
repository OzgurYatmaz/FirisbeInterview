/**
 * This package contains classes for all controllers of the project
 */
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

import com.firisbe.dto.AddCustomerRequestDTO;
import com.firisbe.dto.CustomerDTO;
import com.firisbe.entity.Customer;
import com.firisbe.error.ErrorDetails;
import com.firisbe.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

/**
 * Main customer controller to add new customer to database or fetch customers from database
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

@Tag(name = "Customer controller", description = "Add  and fetch customers")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	/**
	 * 
	 * Customer related operations will be done with this
	 * 
	 */
	@Autowired
	public CustomerService customerService;

	/**
	 * 
	 * Adds customer to database if customer object includes cards it will also adds
	 * cards to database if no cards provided it will associate one default card to
	 * customer.
	 * 
	 * @param Customer object  with or without cards field
	 * @return Http status code 200 with string message
	 * @throws various exception to explaining why customer is not added.
	 * 
	 * @see com.firisbe.error.ResponseErrorHandler class to see possible errors
	 *      might be thrown from here
	 * 
	 */
	@Operation(summary = "Add customer", description = "Adds  new customer to our database")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "When customer is successfully saved to data base"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(schema = @Schema(hidden = true)) }),
			@ApiResponse(responseCode = "409", description = "When submitted data is in conflict with existing data in database or with itself", content = {
					@Content(schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PostMapping("/add-customer")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody AddCustomerRequestDTO customer) throws Exception {

		Customer addedCustomer = null;

		try {
			addedCustomer = customerService.addCustomer(customer);

		} catch (Exception e) {
			throw e;
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Customer with id " + addedCustomer.getId() + " is created: ");
	}

	/**
	 * 
	 * Fetches all customers exist in database.
	 * 
	 * @return List of customer objects
	 * @throws various exception to explaining why customer could not be fetched.
	 * 
	 * @see com.firisbe.error.ResponseErrorHandler class to see possible errors
	 *      might be thrown from here
	 * 
	 */
	@Operation(summary = "Fetch all customers", description = "Fetches all customers exist in our database")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "When error being occured during querying database", content = {
					@Content(schema = @Schema(implementation = ErrorDetails.class)) }) })
	@GetMapping("/get-all-customers")
	public List<CustomerDTO> getAllCustomers() throws Exception {

		List<CustomerDTO> allCustomers = null;

		try {
			allCustomers = customerService.getAllCustomers();
		} catch (Exception e) {
			throw e;
		}

		return CollectionUtils.isEmpty(allCustomers) ? null : allCustomers;

	}

}
