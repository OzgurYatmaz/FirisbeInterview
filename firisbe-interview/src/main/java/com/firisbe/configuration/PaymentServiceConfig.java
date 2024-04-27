package com.firisbe.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "payment-service")
@Component
public class PaymentServiceConfig {

	private String paymentserviceurl;
	private int timeout;
	
	public String getPaymentserviceurl() {
		return paymentserviceurl;
	}
	public void setPaymentserviceurl(String paymentserviceurl) {
		this.paymentserviceurl = paymentserviceurl;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	 
	

}
