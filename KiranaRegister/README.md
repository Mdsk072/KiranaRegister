# Kirana Register Application

## Overview
This Spring Boot application manages transactions for Kirana stores, supporting features like transaction recording, currency conversion, and transaction retrieval.

## Key Components
- **TransactionController**: Manages HTTP requests and responses, providing endpoints for transaction operations.
- **TransactionService**: Contains business logic for handling transactions and currency conversions.
- **CurrencyConversionService**: Offers functionality to convert transaction amounts between different currencies.
- **TransactionRepository**: Interface for data persistence, utilizing Spring Data JPA.
- **ExceptionHandler**: Exception handling mechanism for the application.


## Running the Application
- Clone the repository.
- Adjust database configurations in application.properties:
- spring.datasource.url=jdbc:mysql://<HOST>:<PORT>/<DB_NAME>
- spring.datasource.username=<USERNAME>
- spring.datasource.password=<PASSWORD>
- Replace placeholders with your database details. Start the application

## Testing using either PostMan or curl
- curl http://localhost:8080/api/transactions
- Invoke-WebRequest -Uri http://localhost:8080/api/transactions -Method POST -ContentType "application/json" -Body '{"amount": 100.00, "currency": "INR", "type": "Credit"}'

Note: Tried to document using openapi doc for somereason not working. Need to check on this.
## Features
- Decoupling and Abstraction: Ensure that different parts of application, like services and repositories, are well-decoupled. Utilize interfaces and abstractions
- Unit Tests: Incorporate unit tests for critical components of your application.
- Concurrency Control: If necessary, add mechanisms to handle race conditions, like using @Version for optimistic locking in JPA entities
- Transactional Annotation: Used @Transactional for save methods that involve database transactions. This ensures that the entire method is executed in a single database transaction and is rolled back if an exception occurs.
- Logging using SLF4j: Proper logging mechanism is used to track all the queries and task. In case of error would be helpful in debuging.
- tried to use swagger but springfox isnt working for springboot 3+ version, also tried to use JavaDoc openApi but didnt work for some reason. need to check further on this.

## API Documentation
tried to use swagger but springfox isnt working for springboot 3+ version, also tried to use JavaDoc openApi but didnt work for some reason. need to check further on this.
http://localhost:8080/swagger-ui/index.html

## Additional Notes
Add any extra information or instructions here.