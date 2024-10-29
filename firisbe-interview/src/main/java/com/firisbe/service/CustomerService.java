package com.firisbe.service;

import com.firisbe.dto.AddCustomerRequestDTO;
import com.firisbe.dto.CustomerDTO;
import com.firisbe.entity.Customer;
import com.firisbe.error.DataInsertionConftlictException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

  Page<CustomerDTO> getAllCustomers(Pageable pageable) throws Exception;

  Customer addCustomer(
      AddCustomerRequestDTO addCustomerRequest) throws DataInsertionConftlictException;
}
