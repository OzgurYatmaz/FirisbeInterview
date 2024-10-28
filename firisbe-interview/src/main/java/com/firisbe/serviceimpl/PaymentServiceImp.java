/**
 * This package contains classes for services - business logics
 */
package com.firisbe.serviceimpl;

import com.firisbe.mapper.PaymentMapper;
import com.firisbe.service.PaymentService;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

/**
 * Main payment service's business logic is executed here
 *
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @throws Various exceptions explaining the reasons of failures.
 * @see com.firisbe.error.ResponseErrorHandler class to see possible errors might be thrown from
 * here
 * @since 2024-05-09
 */
@Service
@AllArgsConstructor
public class PaymentServiceImp implements PaymentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImp.class);

  /**
   * Payment records related database operations are done with this
   */
  private final PaymentRepository paymentRepository;

  /**
   * Card related database operations are done with this
   */
  private final CardRepository cardRepository;

  /**
   * To read configuration parameters from .properties file
   *
   * @see com.firisbe.configuration.PaymentServiceConfig class
   * @see /src/main/resources/application-dev.properties
   */
  private final PaymentServiceConfig configParameters;

  /**
   * Object conversions are done with this
   */
  private final PaymentMapper mapper;

  /**
   * Processes payment by using external payment service provider
   *
   * @param payment request object to carry payment data which consist of payment amount and card
   *                number
   * @throws various exceptions depending on the failure reason.
   */
  @Override
  public void processPayment(PaymentRequestDTO paymentRequest) throws ExternalServiceException {
    // Fetch the card by cardNumber
    if (!cardRepository.existsByCardNumber(paymentRequest.getCardNumber())) {
      throw new RecordsNotExistException("Card not found",
          "Card number " + paymentRequest.getCardNumber() + " is not associated with any customer");
    }

    Card card = cardRepository.findByCardNumber(paymentRequest.getCardNumber()).get(0);

    if (card.getBalance() < paymentRequest.getAmount()) {
      throw new InsufficientCardBalanceException("Card balance is insufficient for this payment",
          "Not enough money in card balance");
    }

    ResponseEntity<String> responseEntity = null;
    try {

      // Call the external payment service provider. Usually conversion is needed in
      // real life projects
      responseEntity = sendPaymentRequestToExternalService(paymentRequest);

    } catch (ResourceAccessException ex) {

      LOGGER.error("Error occurred while accessing external payment service", ex);
      throw new PaymentServiceProviderException(
          "An error occured while accessing external payment service",
          ex.getMessage());

    }

    // Create payment record object for inserting payment info to database.
    Payment payment = mapper.preparePaymetRecordForDataBase(paymentRequest, card);
    processExternalResponse(payment, responseEntity, card.getId());
  }

  /**
   * Processes the payment response received from external payment service provider. If payment is
   * successful updates card balance from database and inserts payment record to database.
   *
   * @param payment object for recording the payment detail to database.
   * @param payment response container received from external payment service provider.
   * @param cardId  to update card balance in database.
   * @throws ExternalServiceException if response status code is not 200.
   */
  private void processExternalResponse(Payment payment, ResponseEntity<String> responseEntity,
      Integer cardId)
      throws ExternalServiceException {
    if (ObjectUtils.isNotEmpty(responseEntity)) {
      // additional confirmation steps might be needed depending on the requirements
      if (responseEntity.getStatusCode() == HttpStatus.OK) {
        // only successful payments are saved to DB for now.
        paymentRepository.save(payment);
        cardRepository.updateBalanceAfterPayment(cardId, payment.getAmount());
      } else {
        LOGGER.error("Payment response received indicates the failure of payment: ");
        throw new ExternalServiceException("Response Code: " + responseEntity.getStatusCode(),
            "Payment failed!");
      }
    }
  }


  /**
   * Sends payment request to external payment service provider.
   *
   * @param payment request object to carry payment data which consist of payment amount and card
   *                number for this simple case. In real life projects before calling external
   *                service; object conversion is done.
   * @throws ExternalServiceException if failure occurs during external service call.
   */
  private ResponseEntity<String> sendPaymentRequestToExternalService(
      PaymentRequestDTO externalRequest) {

    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    RestTemplate restTemplate = restTemplateBuilder
        .setConnectTimeout(Duration.ofMillis(configParameters.getTimeout()))
        .setReadTimeout(Duration.ofMillis(configParameters.getTimeout())).build();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<PaymentRequestDTO> request = new HttpEntity<>(externalRequest, headers);
    try {
      ResponseEntity<String> externalResponse = restTemplate
          .postForEntity(configParameters.getPaymentserviceurl(), request, String.class);
      return externalResponse;
    } catch (Exception e) {
      LOGGER.error("Error while sending request to external payment service provider: ", e);
      throw new ExternalServiceException(
          "Error while sending request to external payment service provider",
          e.getMessage());
    }
  }

  /**
   * Fetches payment records with cutomerNumber or cardNumber or both. Both of the parameters are
   * optional
   *
   * @param cutomerNumber if it is null it will be disregarded in repository.
   * @param cardNumber    if it is null  it will be disregarded in repository.
   * @throws RecordsNotBeingFetchedException if there is a failure in reaching database records.
   * @throws ParametersNotProvidedException  if both of the parameters are not supplied
   */
  @SuppressWarnings("deprecation")
  @Override
  public List<PaymentDTO> findPaymentsBySearchCriteria(String cardNumber, String customerNumber)
      throws Exception {

    if (StringUtils.isEmpty(customerNumber) && StringUtils.isEmpty(cardNumber)) {
      throw new ParametersNotProvidedException("Both Arguments cannot be empty",
          "Please provide cardNumber or customerNumber or both");
    }
    List<Payment> paymentsFetched;
    try {
      paymentsFetched = paymentRepository.findByCardNumberOrCustomerNumber(customerNumber,
          cardNumber);
      List<PaymentDTO> payments =paymentsFetched.stream()
          .map(mapper::convertPaymentEntityToDTO)
          .toList();
      return payments;
    } catch (Exception e) {
      LOGGER.error("Error occurred while fetching payment records from DB: ", e);
      throw new RecordsNotBeingFetchedException(
          "Error occured while fetching payment records from data base",
          e.getMessage());
    }
  }


  /**
   * Fetches payment records for time interval between startDate and endDate.
   *
   * @param startDate, compulsory and definition is self explanatory :) format: YYYY-MM-DD example:
   *                   2024-04-27
   * @param endDate,   compulsory and definition is  self explanatory :) format: YYYY-MM-DD example:
   *                   2024-04-27
   * @throws RecordsNotBeingFetchedException is thrown if there is a failure in reaching database
   *                                         records.
   */
  @Override
  public List<PaymentDTO> getAllPaymentsbyDateInterval(LocalDate startDate, LocalDate endDate)
      throws Exception {
    List<Payment> paymentsFetched;
    try {
      paymentsFetched = paymentRepository.getAllPaymentsBetweenDates(
          convertToLocalDateTime(startDate),
          convertToLocalDateTime(endDate));

                               //=mapper. convertPaymentEntityToDTO(paymentsFetched);
      List<PaymentDTO> payments = paymentsFetched.stream()
          .map(mapper::convertPaymentEntityToDTO)
          .toList();
      return payments;
    } catch (Exception e) {
      LOGGER.error("Error occurred while fetching payment records from data base: ", e);
      throw new RecordsNotBeingFetchedException(
          "Error occured while fetching payment records from data base",
          e.getMessage());
    }
  }

  /**
   * Converts date object to local date time for further database querying.
   *
   * @param dateObject of format: YYYY-MM-DD example: 2024-04-27
   */
  private LocalDateTime convertToLocalDateTime(LocalDate date) {
    return LocalDateTime.of(date, LocalTime.MIDNIGHT);
  }
}
