/**
 * This package is for data transfer objects (DTO) to transfer data over web while communicating with this API
 */
package com.firisbe.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * Objects of this class are used to transfer data over web.
 * 
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */

@Schema(description = "Payment Model Information")
public class PaymentDTO {

	@Schema(description = "payment amount", example = "19.57")
	private double amount;

	@Schema(description = "Card number of the payment made", example = "571-1")
	private String cardNumber;
	@Schema(description = "Payment owner's number", example = "114-1")
	private String customerNumber;

	@Schema(description = "Time when payment is processed", example = "2024-04-26 01:36:09.759075")
	private LocalDateTime paymentDate;

	public PaymentDTO(double amount, String cardNumber, String customerNumber, LocalDateTime paymentDate) {
		this.amount = amount;
		this.cardNumber = cardNumber;
		this.customerNumber = customerNumber;
		this.paymentDate = paymentDate;
	}

	public PaymentDTO() {
	}

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
