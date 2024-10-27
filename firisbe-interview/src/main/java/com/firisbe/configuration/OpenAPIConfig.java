/**
 * This package contains classes for project configurations.
 */
package com.firisbe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Swagger configuration is made from here.
 * <p>
 * Use the link {@link http://localhost:8080/swagger-ui/index.html} to see swagger page of the
 * project.
 * <p>
 * OpenApi is new name for Swagger 3
 *
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-08
 */
@Configuration
public class OpenAPIConfig {


  /**
   * Creates bean of swagger UI object for swagger UI. General API info is set here.
   *
   * @return OpenApi object to be used by swagger api internally
   */
  @Bean
  public OpenAPI myOpenAPI() {

    Contact contact = new Contact();
    contact.setEmail("ozguryatmaz@yandex.com");
    contact.setName("Ozgur Yatmaz");
    contact.setUrl("https://www.linkedin.com/in/ozguryatmaz");

    License mitLicense = new License().name("MIT License")
        .url("https://choosealicense.com/licenses/mit/");

    Info info = new Info().title("SecurePay API for Firisbe Interview").version("1.0")
        .contact(contact).description(
            "This API exposes endpoints to use sample payment service. Service anables saving customers and cards of the customers to mysql database "
                + "and requesting payments to any external payment service providers. And if external payment service"
                + " confirms that the payment is made card balance is updated and payment is recorded to payments table."
                + " Lastly, all payments can be queried by date interval or curtomer number or card number.<br />\r\n"
                + "     <br /> Tech Stack:\r\n" + "     <br />\r\n"
                + "     <br /> Language: Java 17\r\n"
                + "     <br /> Framework: Spring Boot 3.2.5\r\n" + "     <br /> Database: H2\r\n"
                + "     <br /> DB Management: Spring Data JPA\r\n"
                + "     <br /> Unit Tests: JUnit and Maven Surefire for test reports\r\n"
                + "     <br /> Documentation: Swagger 3 - (OpenAPI) \r\n"
                + "     <br /> Build Tool: Maven")
        .license(mitLicense);

    return new OpenAPI().info(info);
  }


  /**
   * For Disabling CORS for swagger tests from docker container
   */
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    source.registerCorsConfiguration("/v3/api-docs", config);
    return new CorsFilter(source);
  }


}