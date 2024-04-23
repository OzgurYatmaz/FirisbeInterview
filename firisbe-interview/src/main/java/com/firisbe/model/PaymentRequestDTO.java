package com.firisbe.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequestDTO {

	@NotNull(message = "Card number cannot be null")
	private String cardNumber;
	
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
