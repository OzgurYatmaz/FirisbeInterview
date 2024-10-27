package com.firisbe.service;

import com.firisbe.dto.AddCustomerRequestDTO;
import com.firisbe.dto.CustomerDTO;
import com.firisbe.entity.Customer;
import com.firisbe.error.DataInsertionConftlictException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

  List<CustomerDTO> getAllCustomers() throws Exception;

  Customer addCustomer(
      AddCustomerRequestDTO addCustomerRequest) throws DataInsertionConftlictException;
}
