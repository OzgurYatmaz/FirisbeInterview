/**
 * This package is for automatic creation of database tables by JPA.
 * And objects of the classes classes here are used by JPA for database operations.
 */
package com.firisbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 * This class is auto converted to table in database automatically by JPA
 * Corresponding table name is cards and card info of customers are kept there.
 * 
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */

@Hidden//To hide this class from Swagger UI as it is not DTO
@Entity
@Table(name = "cards")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	private String cardNumber;
	@JsonIgnore//will be fetched from Customer object
	private String customerNumber;
	private double balance;


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
