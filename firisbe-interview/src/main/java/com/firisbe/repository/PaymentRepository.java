package com.firisbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import com.firisbe.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	// Custom query to search payments by card number or customer number
	List<Payment> findByCardNumberOrCustomerNumber(String cardNumber, String customerNumber);

	List<Payment> findByCardNumberAndCustomerNumber(String cardNumber, String customerNumber);

//	@Query(value = "SELECT * from payment as p where  "
//			+ "  ( :customerNumber IS NULL OR p.customer_number = :customerNumber) "
//			+ " AND  ( :cardNumber IS NULL OR p.card_number = :cardNumber)", nativeQuery = true)
//	List<Payment> findByCardNumberOrCustomerNumber2(@Param("customerNumber") String customerNumber,
//			@Param("cardNumber") String cardNumber);
}