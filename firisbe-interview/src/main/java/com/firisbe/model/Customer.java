package com.firisbe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@NotNull(message = "Custmer  number cannot be null")
	@NotBlank(message = "Customer Number may not be empty or blank")
	private String customerNumber;
	@NotNull(message = "Email cannot be null")
	@Email(message = "Invalid email")
	private String email;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // so that customer_ıd column will be created in card
	@JoinColumn(name = "customer_id") // This creates a foreign key column in the Card table
	@JsonIgnore // not needed to be returned in response
	private List<Card> cards;

	// No-argument constructor (for JPA)
	public Customer() {
	}

	public Customer(String name, String customerNumber, String email, List<Card> cards) {
		this.name = name;
		this.customerNumber = customerNumber;
		this.email = email;
		this.cards = cards;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	@JsonIgnore // when records are fetched card info wont be visible
	public List<Card> getCards() {
		return cards;
	}

	@JsonProperty // such that card field will be readable from request body
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", customerNumber=" + customerNumber + ", email=" + email
				+ ", cards=" + cards + "]";
	}

}
