package com.firisbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firisbe.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public List<Customer> findByEmail(String email);
}