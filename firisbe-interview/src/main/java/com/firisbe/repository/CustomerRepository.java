package com.firisbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firisbe.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	// @Query( "SELECT * FROM Task WHERE Task.user_id = :user_id")
	// public List<Task> findByUser_idOrderByDescription(@Param("user_id") String
	// userId);

	// public List<Task> findByUser_idOrderByGivenDate(@Param("user_id") String
	// userId);
    public List<Customer> findByEmail(String email);
}