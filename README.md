# User Authentication Project (In Development)
This project is a learning exercise focused on implementing user authentication using **Java** and **Spring Boot**. It serves as a foundation for handling user-related workflows such as registration, login, and data persistence using modern Java and Spring features.
## Purpose of This Project
This project is under active development and is designed to:
- Learn and implement secure user authentication.
- Explore the integration of **Spring Boot**, **JPA/Hibernate**, and **JUnit testing** for a robust backend.
- Demonstrate best practices in database persistence and entity management for user data.

## Features (Current and Planned)
### Current Features:
- **User registration** with basic validation (unique username/email).
- **Unit Tests** to ensure correct implementation of entity persistence and constraints.
- **Spring Boot** application with JPA and H2 database for testing.

### Planned Features:
- Integration with **Spring Security** for authentication.
- Role-based user management (admin/user roles).
- Enhanced exception handling for user registration.
- Implementation of OAuth2 or JWT-based authorization tokens.
- Password encryption for secure storage.

## Project Structure
``` plaintext
src/main/java/com/example/userauth
├── controller         # REST API controllers (to be implemented)
├── model              # User entity and domain models
├── repository         # Data access layer for database operations
├── service            # Business logic and service layer (WIP)
└── UserAuthApplication.java    # Main application entry point

src/test/java/com/example/userauth
├── model              # Unit tests for User entity constraints
├── repository         # Tests for data access and repository behavior
└── UserAuthApplicationTests.java   # Application context load test

src/main/resources
├── application.properties    # Application configuration (e.g., database, ports)
└── static/                   # (Optional) Frontend resources (if applicable)

pom.xml                       # Maven configuration for dependencies and build
```
## How to Run (Development Phase)
### Prerequisites
- **Java 21**
- **Maven** (Latest Version)

### Steps to Run:
1. Clone the repository:
``` bash
   git clone <repository_url>
   cd user-auth-java
```
1. Configure the database connection in `application.properties`:
``` properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driver-class-name=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```
1. Build and run the application:
``` bash
   mvn spring-boot:run
```
1. Use Postman or any HTTP client to test APIs (in progress).

## Testing the Project
Run the test suite with:
``` bash
mvn test
```
The test suite includes scenarios like:
- Valid user persistence.
- Duplicate usernames or emails.
- Handling of null/invalid user fields with exceptions.

### Example Test Structure (Current Tests Included):
- Validation of `Users` entity fields (`null` checks for username/email/password).
- Unique constraint violations for username/email.
- Persistence exceptions (`PropertyValueException`, `ConstraintViolationException`).


## Known Issues (In-Progress Development)
- Passwords are stored as plain text (encryption to be added).
- No authorization or authentication implemented yet.
- API endpoints and controllers are still a work in progress.

## Learning Outcomes
This project is intended to help with:
- Understanding entity persistence with JPA and Hibernate.
- Practicing effective unit testing using `@DataJpaTest`.
- Building a foundational understanding of Spring Security (planned).

As the project progresses, this README should be updated to reflect completed features, accurate API documentation, and better developer guidelines.
This structure keeps the README relevant during development while clearly communicating the project's current state. Let me know if you’d like further help refining any specific area!
