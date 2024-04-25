package com.firisbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firisbe.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Boolean existsByEmail(String email);
    Boolean existsByCustomerNumber(String customerNumber);
}