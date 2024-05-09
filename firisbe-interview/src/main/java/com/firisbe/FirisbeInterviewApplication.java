package com.firisbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * This is the starting point of the project. Run this as java application to
 * run the project
 * 
 * For API documentation and testing use the url below:
 * http://localhost:8080/swagger-ui/index.html
 * 
 * For web document use the link below:
 * https://app.swaggerhub.com/apis-docs/ozguryatmaz/secure-pay_api_for_firisbe_interview/1.0
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */

@SpringBootApplication
public class FirisbeInterviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirisbeInterviewApplication.class, args);
	}

}
