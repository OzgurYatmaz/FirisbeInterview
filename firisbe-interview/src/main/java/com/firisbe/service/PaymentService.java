/**
 * This package contains classes for services - business logics
 */
package com.firisbe.service;

import com.firisbe.configuration.PaymentServiceConfig;
import com.firisbe.dto.PaymentDTO;
import com.firisbe.dto.PaymentRequestDTO;
import com.firisbe.entity.Card;
import com.firisbe.entity.Payment;
import com.firisbe.error.ExternalServiceException;
import com.firisbe.error.InsufficientCardBalanceException;
import com.firisbe.error.ParametersNotProvidedException;
import com.firisbe.error.PaymentServiceProviderException;
import com.firisbe.error.RecordsNotBeingFetchedException;
import com.firisbe.error.RecordsNotExistException;
import com.firisbe.repository.CardRepository;
import com.firisbe.repository.PaymentRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Interface of main payment sevice
 * 
 * @throws Various exceptions explaining the reasons of failures.
 * 
 * @see com.firisbe.error.ResponseErrorHandler class to see possible errors
 *      might be thrown from here
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-10-28
 * 
 */
@Service
public interface PaymentService {



	/**
	 * 
	 * Processes payment by using external payment service provider
	 * 
	 * @param payment request object to carry payment data which consist of payment
	 *                amount and card number
	 * @throws various exceptions depending on the failure reason.
	 * 
	 */
	public void processPayment(PaymentRequestDTO paymentRequest) throws ExternalServiceException;



	/**
	 * 
	 * Fetches payment records with cutomerNumber or cardNumber or both. Both of the
	 * parameters are optional
	 * 
	 * @param cutomerNumber if it is null it will be disregarded in repository.
	 * @param cardNumber    if it is null  it will be disregarded in repository.
	 *
	 * 
	 */
	public Page<PaymentDTO> findPaymentsBySearchCriteria(String cardNumber, String customerNumber, Pageable pageable) throws Exception;

	
	/**
	 * 
	 * Fetches payment records for time interval between startDate and endDate.
	 * 
	 * @param startDate, compulsory and definition is self explanatory :) format: YYYY-MM-DD example: 2024-04-27
	 * @param endDate, compulsory and definition is  self explanatory :) format: YYYY-MM-DD example: 2024-04-27
	 *.
	 *  
	 */
	public Page<PaymentDTO> getAllPaymentsbyDateInterval(LocalDate startDate, LocalDate endDate, Pageable pageable) throws Exception;

}
