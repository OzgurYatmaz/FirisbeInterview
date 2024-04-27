package com.firisbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cards")
@Schema(description = "Card Model Information")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	@Schema(description = "Card number", example = "571-1")
	private String cardNumber;
	@Schema(description = "Customer number of that card belong", example = "114-1")
	@JsonIgnore//will be fetched from Customer object
	private String customerNumber;
	@Schema(description = "Balance of the card", example = "1000.00")
//	@JsonIgnore
	private double balance;

	// No-argument constructor (for JPA)
	public Card() {
	}

//	@JsonProperty
	public double getBalance() {
		return balance;
	}

//	@JsonIgnore
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
