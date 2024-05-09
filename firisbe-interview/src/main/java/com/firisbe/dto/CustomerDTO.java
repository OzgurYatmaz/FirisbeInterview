/**
 * This package is for data transfer objects (DTO) to transfer data over web while communicating with this API
 */
package com.firisbe.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * Objects of this class are used to transfer data over web.
 * 
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */

@Schema(description = "Customer DTO for fetch all customers response")
public class CustomerDTO {

	@Schema(description = "Customer's name", example = "Ozgur")
	private String name;

	@Schema(description = "Unique customer number", example = "114-1")
	private String customerNumber;
	@Schema(description = "Customer's email address must be unique", example = "ozguryatmaz@yandex.com")
	private String email;

	 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

}
