package com.firisbe.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Payment Request Model Information")
public class PaymentRequestDTO {

	@NotNull(message = "Card number cannot be null")
	@NotBlank(message = "Card Number may not be empty or blank")
	@Schema(description = "Number of the card which will be associated to payment", example = "571-1")
	private String cardNumber;
	
	@NotNull(message = "Payment amount cannot be null")
	@Positive(message = "Payment amount should be greater than nothing :) ")
	@Schema(description = "Customer's name", example = "Ozgur")
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
