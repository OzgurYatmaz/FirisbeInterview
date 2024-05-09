/**
 * This is package for interfaces responsible for database operations
 */
package com.firisbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firisbe.entity.Customer;

/**
 * 
 * Customer related operations in database are done here
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-08
 * 
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	/**
	 * 
	 * Checks if the provided email is already associated with any customer in
	 * database.
	 * 
	 * @param email
	 * 
	 */
	public Boolean existsByEmail(String email);

	/**
	 * 
	 * Checks if the provided customer number is already associated with any
	 * customer in database.
	 * 
	 * @param email
	 * 
	 */
	public Boolean existsByCustomerNumber(String customerNumber);
}