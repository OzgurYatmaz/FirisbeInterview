package com.firisbe.mapper;

import com.firisbe.dto.PaymentDTO;
import com.firisbe.dto.PaymentRequestDTO;
import com.firisbe.entity.Card;
import com.firisbe.entity.Payment;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Main  interface for list of conversion operations for customer service
 *
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-10-27
 */
@Mapper(componentModel = "spring")
public abstract class PaymentMapper {



  /**
   *
   * Prepares payment record object to create payment record in database.
   *
   * @param paymentRequest request object to carry payment data which consist of payment
   *                amount and card number.
   * @param card    object for card info from which payment will be done.
   *
   */
  public Payment preparePaymetRecordForDataBase(PaymentRequestDTO paymentRequest, Card card) {
    Payment payment = new Payment();
    payment.setCardNumber(card.getCardNumber());
    payment.setAmount(paymentRequest.getAmount());
    LocalDateTime paymentTime = LocalDateTime.now();
    payment.setPaymentDate(paymentTime);
    payment.setCustomerNumber(card.getCustomerNumber());
    return payment;
  }




  /**
   * Entity object fetched from database is converted to DTO object for web service return.
   *
   * @param paymentsFetched of entity objects fetched from database.
   * @return List of DTO objects for API return type.
   */
  @Mapping(target = "amount", source = "amount")
  @Mapping(target = "customerNumber", source = "customerNumber")
  @Mapping(target = "cardNumber", source = "cardNumber")
  @Mapping(target = "paymentDate", source = "paymentDate")
  public abstract PaymentDTO convertPaymentEntityToDTO(Payment paymentsFetched);

//  private List<PaymentDTO> convertPaymentEntityToDTO(List<Payment> paymentsFetched) {
//    if (!CollectionUtils.isEmpty(paymentsFetched)) {
//      List<PaymentDTO> paymentsForResponse = new ArrayList<PaymentDTO>();
//      for (Payment p : paymentsFetched) {
//        PaymentDTO tempPayment = new PaymentDTO();
//        tempPayment.setAmount(p.getAmount());
//        tempPayment.setCustomerNumber(p.getCustomerNumber());
//        tempPayment.setCardNumber(p.getCardNumber());
//        tempPayment.setPaymentDate(p.getPaymentDate());
//        paymentsForResponse.add(tempPayment);
//      }
//
//      return paymentsForResponse;
//
//    }
//    return null;
//  }

//
//
//  public List<PaymentDTO> convertPaymentEntityListsToDTOLists(List<Payment> paymentsFetched) {
//    return paymentsFetched == null || paymentsFetched.isEmpty() ? null :
//        paymentsFetched.stream()
//            .map(p -> {
//              PaymentDTO tempPayment = new PaymentDTO();
//              tempPayment.setAmount(p.getAmount());
//              tempPayment.setCustomerNumber(p.getCustomerNumber());
//              tempPayment.setCardNumber(p.getCardNumber());
//              tempPayment.setPaymentDate(p.getPaymentDate());
//              return tempPayment;
//            })
//            .collect(Collectors.toList());
//  }

}
