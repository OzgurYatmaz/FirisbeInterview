/**
 * This package contains classes for services - business logics
 */
package com.firisbe.service;

import com.firisbe.dto.PaymentDTO;
import com.firisbe.dto.PaymentRequestDTO;
import com.firisbe.error.ExternalServiceException;
import com.firisbe.filter.PaymentFilter;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Interface of main payment sevice
 *
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @see com.firisbe.error.ResponseErrorHandler class to see possible errors might be thrown from
 * here
 * @since 2024-10-28
 */
@Service
public interface PaymentService {


  /**
   * Processes payment by using external payment service provider
   *
   * @param paymentRequest object to carry payment data which consist of payment amount and card
   *                       number
   * @throws various exceptions depending on the failure reason.
   */
  void processPayment(PaymentRequestDTO paymentRequest) throws ExternalServiceException;


  /**
   * Fetches payment records with cutomerNumber or cardNumber or both. Both of the parameters are
   * optional
   *
   */
  Page<PaymentDTO> findPaymentsBySearchCriteria(PaymentFilter filter,
      Pageable pageable) throws Exception;


  /**
   * Fetches payment records for time interval between startDate and endDate.
   *
   * @param startDate, compulsory and definition is self explanatory :) format: YYYY-MM-DD example:
   *                   2024-04-27
   * @param endDate,   compulsory and definition is  self explanatory :) format: YYYY-MM-DD example:
   *                   2024-04-27 .
   */
  Page<PaymentDTO> getAllPaymentsbyDateInterval(LocalDate startDate, LocalDate endDate,
      Pageable pageable) throws Exception;

}
