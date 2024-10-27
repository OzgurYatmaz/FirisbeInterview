# Spring Boot Project for Firisbe Interview

This is a case project for a job interview which simulates a payment service. 
The project description document is [here](API-Documents/Firisbe_java_case_study(mid).pdf).

The improved version of the project is [here](https://github.com/OzgurYatmaz/MultiPayAPI) (External payment service providers are as many as needed and selectable at request).

### How to Run the Project

0. Use the docker image provided bellow or continue from step 1 bellow:
1. Build a Jar with maven install command at `/target/firisbe-interview-1.0.0.jar`.
2. Copy the jar to desired location.
3. Run the jar with command: `java -jar firisbe-interview-1.0.0.jar`.

### How to Run the Project in IDE

1. Import the project as existing Maven projects.
2. Run `com.firisbe.FirisbeInterviewApplication` as a Java Application.

### How to Test API

1. Use the Mockoon collection provided [here](API-Documents/Mockoon%20Collection%20for%20Mock%20Service) to mock a sample external payment service provider.
2. Use the URL below to test with swagger: <br>
   http://localhost:8080/swagger-ui/index.html (If you use docker container port number will be specified at docker run)

### Online Document

1. Use the link below to see published online Swagger document: <br>
   https://app.swaggerhub.com/apis-docs/ozguryatmaz/secure-pay_api_for_firisbe_interview/1.0

### Docker Image

1. Use the link below to see published Docker image: <br>
   https://hub.docker.com/r/ozguryatmaz/firisbe-interview <br>
   (Note: Docker internal port number is 8080)<br>
   Run the command: `docker run -p any_port_number_you_wish_to_use:8080 image_id` after pulling the image.

### Test Tools, Reports and More

1. [Test Reports Tools and More](API-Documents)

### Tech Stack:

- Language: Java 17
- Framework: Spring Boot 3.2.5
- Database: H2 (Embedded DB)
- DB Management: Spring Data JPA
- Unit Tests: JUnit and Maven Surefire for functionality test reports
- Integration Tests: Mockito
- Documentation: Swagger 3 (OpenAPI)
- Build Tool: Maven 
