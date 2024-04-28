package com.firisbe.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.firisbe.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	@Query(value = "SELECT * from payments as p where  "
			+ "  ( :customerNumber IS NULL OR p.customer_number = :customerNumber) "
			+ " AND  ( :cardNumber IS NULL OR p.card_number = :cardNumber)", nativeQuery = true)
	List<Payment> findByCardNumberOrCustomerNumber(@Param("customerNumber") String customerNumber,
			@Param("cardNumber") String cardNumber);

	@Query(value = "SELECT * from payments as p where p.payment_date BETWEEN :startDate AND :endDate", nativeQuery = true)
	public List<Payment> getAllPaymentsBetweenDates(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);
}