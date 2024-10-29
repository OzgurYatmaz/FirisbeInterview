/**
 * This package is for interfaces responsible for database operations
 */
package com.firisbe.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.firisbe.entity.Payment;

/**
 * Payment related operations in database are done here
 *
 * @author Ozgur Yatmaz
 * @version 2.0.0
 * @since 2024-10-29
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer>,
    QuerydslPredicateExecutor<Payment> {

  /**
   * Fetches payment records for time interval between startDate and endDate.
   *
   * @param startDate, compulsory and definition is self explanatory :)
   * @param endDate,   compulsory and definition is  self explanatory :)
   * @param pageable   for pagination
   */
  @Query(value = "SELECT * from payments as p where p.payment_date BETWEEN :startDate AND :endDate", nativeQuery = true)
  Page<Payment> getAllPaymentsBetweenDates(@Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate, Pageable pageable);
}