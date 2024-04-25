package com.firisbe;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.firisbe.model.Payment;
import com.firisbe.repository.PaymentRepository;

@SpringBootTest
class FirisbeInterviewApplicationTests {

	@Autowired
	private PaymentRepository paymentRepository;

	@Test
	void findByDepartmentTest() {
		List<Payment> payment = paymentRepository.findByCardNumberOrCustomerNumber("571-1","114-1");

		payment.forEach((p) -> {
			System.out.println(p.getId());
			System.out.println(p.getAmount());
			System.out.println(p.getCustomerNumber());
			System.out.println(p.getCardNumber());
			System.out.println(p.getPaymentDate());
		});
	}

//	@AfterEach
//	public void clean() {
//		paymentRepository.deleteAll();
//	}
}
