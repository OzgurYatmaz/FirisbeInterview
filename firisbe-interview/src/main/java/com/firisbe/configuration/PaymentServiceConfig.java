/**
 * This package contains classes for project configurations.
 */
package com.firisbe.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class reads values from application.properties file.
 *
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @see /src/main/resources/application-dev.properties
 * @since 2024-05-08
 */
@ConfigurationProperties(prefix = "payment-service")
@Component
public class PaymentServiceConfig {

  /**
   * Uri of the external payment service provider
   */
  private String paymentserviceurl;

  /**
   * For connection timeout time in milliseconds when sending request to external payment service
   * provider
   */
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
