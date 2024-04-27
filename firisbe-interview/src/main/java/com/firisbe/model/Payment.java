package com.firisbe.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
@Schema(description = "Payment Model Information")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	@Schema(description = "payment amount", example = "19.57")
	private double amount;
	
	@Column(name="card_number")
	@Schema(description = "Card number of the payment made", example = "571-1")
	private String cardNumber;
	@Column(name="customer_number")
	@Schema(description = "Payment owner's number", example = "114-1")
	private String customerNumber;

	@Schema(description = "Time when payment is processed", example = "2024-04-26 01:36:09.759075")
	private LocalDateTime paymentDate;

	// No-argument constructor (for JPA)
	public Payment() {
	}

	 

	public Payment(double amount, String cardNumber, String customerNumber, LocalDateTime paymentDate) {
		this.amount = amount;
		this.cardNumber = cardNumber;
		this.customerNumber = customerNumber;
		this.paymentDate = paymentDate;
	}




	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}



	public String getCustomerNumber() {
		return customerNumber;
	}



	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

}
