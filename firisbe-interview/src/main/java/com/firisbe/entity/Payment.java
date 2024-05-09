/**
 * This package is for automatic creation of database tables by JPA.
 * And objects of the classes classes here are used by JPA for database operations.
 */
package com.firisbe.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 * This class is auto converted to table in database automatically by JPA
 * Corresponding table name is cards and payment info of customers are kept there.
 * 
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */

@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private double amount;
	
	@Column(name="card_number")
	private String cardNumber;
	@Column(name="customer_number")
	private String customerNumber;

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
