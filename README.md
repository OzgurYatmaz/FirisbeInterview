# Spring Boot Project for Firisbe Interview 
This is a case project for Job interview which simulates a payment service.  
Project description document is  <a href=API-Documents/Firisbe_java_case_study(mid).pdf>here</a>

### How to run the project

1. Build a Jar with maven install command at /target/firisbe-interview-1.0.0.jar
2. Copy the jar to desired location
2. Run the jar with comand: java -jar firisbe-interview-1.0.0.jar

### How to run the project in IDE

1. Import the project as existing maven projects 
2. Run com.firisbe.FirisbeInterviewApplication as a Java Application.

### How to test api
1. Use the Mockoon collection provided <a href=API-Documents/Mockoon%20Collection%20for%20Mock%20Service>here</a> to mock sample external payment service provider

2. Use the url below or provided postman collection provided in API-Documents folder  <br>
   http://localhost:8080/swagger-ui/index.html


### Online Document

1. Use the link below to see published online swagger document:<br>

https://app.swaggerhub.com/apis-docs/ozguryatmaz/secure-pay_api_for_firisbe_interview/1.0

### Test tools, Reports and More

1.  <a href=API-Documents>Test Reports Tools and More</a><br>

### Tech Stack

Language: Java 17 <br>
Framework: Spring Boot 3.2.5 <br>
Database: MySql <br>
DB Management: Spring Data JPA <br>
Unit Tests: JUnit and Maven Surefire for functionality test reports <br>
Build Tool: Maven 
