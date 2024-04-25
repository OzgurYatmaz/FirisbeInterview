package com.firisbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.firisbe.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	// Custom query to search payments by card number or customer number
    List<Payment> findByCardNumberOrCustomerNumber(String cardNumber, String customerNumber);

}