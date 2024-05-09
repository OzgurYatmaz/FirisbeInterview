/**
 * This package is for data transfer objects (DTO) to transfer data over web
 */
package com.firisbe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 
 * Objects of this class are used to transfer data over web.
 * Request body of make payment operation
 * 
 * @see com.firisbe.controller.PaymentController.makePayment(PaymentRequestDTO)
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */

@Schema(description = "Payment Request Model Information")
public class PaymentRequestDTO {

	@NotNull(message = "Card number cannot be null")
	@NotBlank(message = "Card Number may not be empty or blank")
	@Schema(description = "Number of the card which will be associated to payment", example = "571-1")
	private String cardNumber;
	
	@NotNull(message = "Payment amount cannot be null")
	@Positive(message = "Payment amount should be positive:) ")
	@Schema(description = "Amount to be paid", example = "45.11")
	private double amount;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
