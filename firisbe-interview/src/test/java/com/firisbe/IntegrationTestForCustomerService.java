package com.firisbe;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firisbe.controller.CustomerController;
import com.firisbe.dto.AddCustomerRequestDTO;
import com.firisbe.dto.CustomerDTO;
import com.firisbe.entity.Customer;
import com.firisbe.service.CustomerService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * This class is for integration tests of the Customer service.
 *
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-10-27
 */
@WebMvcTest(CustomerController.class) // Loads only the specified controller
public class IntegrationTestForCustomerService {

  @Autowired
  private MockMvc mockMvc;

  @MockBean // Mock the service layer
  private CustomerService customerService;


  @Test
  public void testGetAllCustomers() throws Exception {
    List<CustomerDTO> mockCustomers = List.of(
        new CustomerDTO("ozgur yatmaz", "114-1", "o.y@firisby.com"),
        new CustomerDTO("ozgur2 yatmaz2", "114-2", "o2.y@firisby.com")
    );

    when(customerService.getAllCustomers()).thenReturn(mockCustomers);

    // Act & Assert: Test the endpoint with a valid ID
    mockMvc.perform(get("/customer/get-all-customers")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$[0].name").value("ozgur yatmaz"))
        .andExpect(jsonPath("$[0].customerNumber").value("114-1"))
        .andExpect(jsonPath("$[0].email").value("o.y@firisby.com"))
        .andExpect(jsonPath("$[1].name").value("ozgur2 yatmaz2"))
        .andExpect(jsonPath("$[1].customerNumber").value("114-2"))
        .andExpect(jsonPath("$[1].email").value("o2.y@firisby.com"));
  }

  @Test
  public void testAddCustomer() throws Exception {

    AddCustomerRequestDTO customerRequest = new AddCustomerRequestDTO("ozgur yatmaz", "114-1",
        "o.y@firisby.com", null);
    Customer mockCustomer = new Customer("ozgur yatmaz", "114-1", "o.y@firisby.com", null);
    mockCustomer.setId(19);
//  İf this line is used no need to override equals method of the dto as no need for object equivalence.
//    when(customerService.addCustomer(any(AddCustomerRequestDTO.class))).thenReturn(mockCustomer);
    when(customerService.addCustomer(customerRequest)).thenReturn(mockCustomer);

    mockMvc.perform(post("/customer/add-customer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(customerRequest)))
        .andExpect(status().isCreated())
        .andExpect(content().string("Customer with id 19 is created: "))
        .andDo(print());
  }
}
