package com.firisbe.configuration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

///http://localhost:8080/swagger-ui/index.html
@Configuration//Open api is new name forSwagger 3
public class OpenAPIConfig {

  @Value("${openapi.dev-url}")
  private String devUrl;

  @Value("${openapi.prod-url}")
  private String prodUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");

    Contact contact = new Contact();
    contact.setEmail("ozguryatmaz@yandex.com");
    contact.setName("Ozgur Yatmaz");
    contact.setUrl("https://www.linkedin.com/in/ozguryatmaz");

    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("SecurePay API for Firisbe Interview")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to use sample payment service. Service anables saving customers and cards of the customers to mysql database "
        		+ "and requesting payments to any external payment service providers. And if external payment service"
        		+ " confirms that the payment is made card balance is updated and payment is recorded to payments table."
        		+ " Lastly, all payments can be queried by date interval or curtomer number or card number.").termsOfService("https://www.bezkoder.com/terms")
        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}