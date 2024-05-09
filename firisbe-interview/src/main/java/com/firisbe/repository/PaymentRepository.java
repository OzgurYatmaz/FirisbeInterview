/**
 * This package is for interfaces responsible for database operations
 */
package com.firisbe.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.firisbe.entity.Payment;

/**
 * 
 * Payment related operations in database are done here
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-08
 * 
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	/**
	 * 
	 * Fetches payment records with cutomerNumber or cardNumber or both. Both of the
	 * parameters are optional
	 * 
	 * @param cutomerNumber if it is not provided it will be disregarded
	 * @param cardNumber    if it is not provided it will be disregarded
	 * 
	 */
	@Query(value = "SELECT * from payments as p where  "
			+ "  ( :customerNumber IS NULL OR p.customer_number = :customerNumber) "
			+ " AND  ( :cardNumber IS NULL OR p.card_number = :cardNumber)", nativeQuery = true)
	public List<Payment> findByCardNumberOrCustomerNumber(@Param("customerNumber") String customerNumber,
			@Param("cardNumber") String cardNumber);

	/**
	 * 
	 * Fetches payment records for time interval between startDate and endDate.
	 * 
	 * @param startDate, compulsory and definition is self explanatory :)
	 * @param endDate, compulsory and definition is  self explanatory :)
	 * 
	 */
	@Query(value = "SELECT * from payments as p where p.payment_date BETWEEN :startDate AND :endDate", nativeQuery = true)
	public List<Payment> getAllPaymentsBetweenDates(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);
}