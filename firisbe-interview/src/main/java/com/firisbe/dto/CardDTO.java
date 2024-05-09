/**
 * This package is for data transfer objects (DTO) to transfer data over web
 */
package com.firisbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Schema(description = "Card Model Information")
public class CardDTO {

	@Schema(description = "Card number", example = "571-1")
	private String cardNumber;
	@Schema(description = "Customer number of that card belong", example = "114-1")
	@JsonIgnore // will be fetched from Customer object
	private String customerNumber;
	@Schema(description = "Balance of the card", example = "1000.00")
	private double balance;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

}
