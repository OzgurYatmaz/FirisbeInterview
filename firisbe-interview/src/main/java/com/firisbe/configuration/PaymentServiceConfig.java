package com.firisbe.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "payment-service")
@Component
public class PaymentServiceConfig {

	private String paymentserviceurl;

	// for sample purpose these can be as many as needed
	private String clientid;
	private String clientsecret;

	public String getPaymentserviceurl() {
		return paymentserviceurl;
	}

	public void setPaymentserviceurl(String paymentserviceurl) {
		this.paymentserviceurl = paymentserviceurl;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getClientsecret() {
		return clientsecret;
	}

	public void setClientsecret(String clientsecret) {
		this.clientsecret = clientsecret;
	}

}
