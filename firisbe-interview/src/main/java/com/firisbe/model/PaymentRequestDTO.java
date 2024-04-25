package com.firisbe.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequestDTO {

	@NotNull(message = "Card number cannot be null")
	@NotBlank(message = "Card Number may not be empty or blank")
	private String cardNumber;
	
	@NotNull(message = "Payment amount cannot be null")
	@Positive(message = "Payment amount should be greater than nothing :) ")
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
