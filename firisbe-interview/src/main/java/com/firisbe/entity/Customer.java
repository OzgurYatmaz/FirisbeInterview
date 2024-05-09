/**
 * This package is for automatic creation of database tables by JPA.
 * And objects of the classes classes here are used by JPA for database operations.
 */
package com.firisbe.entity;

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
import jakarta.persistence.Table;

/**
 * 
 * This class is auto converted to table in database automatically by JPA
 * Corresponding table name is customers and customer info is kept there.
 * 
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	private String name;
	private String customerNumber;
	private String email;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // so that customer_ıd column will be created in card
	@JoinColumn(name = "customer_id") // This creates a foreign key column in the Card table
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
