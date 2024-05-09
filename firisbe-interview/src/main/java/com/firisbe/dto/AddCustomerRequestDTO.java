/**
 * This package is for data transfer objects (DTO) to transfer data over web while communicating with this API
 */
package com.firisbe.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 
 * Objects of this class are used to transfer data over web.
 * Request body of add customer operation 
 * 
 * @see com.firisbe.controller.CustomerController.addCustomer(AddCustomerRequestDTO)
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-09
 * 
 */

@Schema(description = "Customer Model Information")
public class AddCustomerRequestDTO {
	

	@NotNull(message = "Custmer  name cannot be null")
	@NotBlank(message = "Customer name may not be empty or blank")
	@Schema(description = "Customer's name", example = "Ozgur")
	private String name;
	@NotNull(message = "Custmer  number cannot be null")
	@NotBlank(message = "Customer Number may not be empty or blank")
	@Schema(description = "Unique customer number", example = "114-1")
	private String customerNumber;
	@NotNull(message = "Email cannot be null")
	@Email(message = "Invalid email")
	@Schema(description = "Customer's email address must be unique", example = "ozguryatmaz@yandex.com")
	private String email;
	
	@JsonIgnore // not needed to be returned in response
	@Schema(description = "Cards of customer")
	private List<CardDTO> cards;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore // when records are fetched card info wont be visible
	public List<CardDTO> getCards() {
		return cards;
	}

	@JsonProperty // such that card field will be readable from request body
	public void setCards(List<CardDTO> cards) {
		this.cards = cards;
	}
	
	
	
	
	
	
}
