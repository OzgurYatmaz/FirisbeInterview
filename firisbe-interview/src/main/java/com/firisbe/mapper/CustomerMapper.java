package com.firisbe.mapper;

import com.firisbe.dto.AddCustomerRequestDTO;
import com.firisbe.dto.CardDTO;
import com.firisbe.dto.CustomerDTO;
import com.firisbe.entity.Card;
import com.firisbe.entity.Customer;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.CollectionUtils;

/**
 * Main  interface for list of conversion operations for customer service
 *
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-10-27
 */
@Mapper(componentModel = "spring")
public abstract class CustomerMapper {


  /**
   * Entity object fetched from database is converted to DTO object for web service return.
   *
   * @param customersFetched: customers fetched from database.
   * @return DTO object for API return type.
   */
  @Mapping(target = "name", source = "name")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "customerNumber", source = "customerNumber")
  public abstract CustomerDTO convertCustomerEntityToDTO(Customer customersFetched);


  /**
   * request dto object is being converted to entity object to be handled JPA.
   *
   * @param addCustomerRequest body object for transmitting data in web.
   * @return customer entity object to be saved database by JPA.
   */
  public Customer convertDTO_To_Entity(AddCustomerRequestDTO addCustomerRequest) {

    Customer customer = new Customer();
    customer.setName(addCustomerRequest.getName());
    customer.setEmail(addCustomerRequest.getEmail());
    customer.setCustomerNumber(addCustomerRequest.getCustomerNumber());
    List<CardDTO> cards = addCustomerRequest.getCards();
    if (!CollectionUtils.isEmpty(cards)) {
      List<Card> newCards = new ArrayList<>();
      for (CardDTO card : cards) {
        Card tempCard = new Card();
        tempCard.setCardNumber(card.getCardNumber());
        tempCard.setCustomerNumber(addCustomerRequest.getCustomerNumber());
        tempCard.setBalance(card.getBalance());
        newCards.add(tempCard);
      }
      customer.setCards(newCards);
    }
    return customer;
  }

}
