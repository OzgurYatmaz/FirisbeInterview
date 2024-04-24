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

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // so that customer_ıd column will be created in card
	@JoinColumn(name = "customer_id") // This creates a foreign key column in the Card table
	@JsonIgnore // not needed to be returned in response
	private List<Card> cards;

	// No-argument constructor (for JPA)
	public Customer() {
	}

	public Customer(String name, List<Card> cards) {
        this.name = name;
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", cards=" + cards + "]";
	}

}
