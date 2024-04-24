package com.firisbe.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firisbe.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

//	 List<Payment> findByCard_CardNumber(String cardNumber);
//
//	 List<Payment> findByCard_Customer_Id(Integer customerId);
//	 
//	 List<Payment> findByPaymentDateBetween(Date startDate, Date endDate);
}