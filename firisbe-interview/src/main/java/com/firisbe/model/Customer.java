package com.firisbe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customers")
@Schema(description = "Customer Model Information")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	@NotNull(message = "Custmer  name cannot be null")
	@NotBlank(message = "Customer name may not be empty or blank")
	@Schema(description = "Customer's name", example = "Ozgur")
	private String name;
	@NotNull(message = "Custmer  number cannot be null")
	@NotBlank(message = "Customer Number may not be empty or blank")
	@Schema(description = "Unique customer number for name conflict", example = "114-1")
	private String customerNumber;
	@NotNull(message = "Email cannot be null")
	@Email(message = "Invalid email")
	@Schema(description = "Customer's email address must be unique", example = "ozguryatmaz@yandex.com")
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
