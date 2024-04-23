package com.firisbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firisbe.model.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

	List<Card> findByCardNumber(String cardNumber);

}