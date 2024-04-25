package com.firisbe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String cardNumber;
	private String customerNumber;

//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name = "card_id") // This creates a foreign key column in the Payment table
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore // not needed to be returned in response
	private List<Payment> payments;

	// No-argument constructor (for JPA)
	public Card() {
	}

	 

	public Card(String cardNumber, String customerNumber, List<Payment> payments) {
		this.cardNumber = cardNumber;
		this.customerNumber = customerNumber;
		this.payments = payments;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
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



	@Override
	public String toString() {
		return "Card [id=" + id + ", cardNumber=" + cardNumber + ", payments=" + payments + "]";
	}


 

}
