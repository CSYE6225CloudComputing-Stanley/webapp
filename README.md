## Technologies Used
- **Java 21**
- **Spring Boot 3.x**
- **Spring Web**
- **Spring Data JPA**
- **MySQL**

## Getting Started

### Prerequisites
- **Maven 3+**

### Database Configuration
Set environment variables in `src/main/resources/application-dev.yml`.

If you are using a Unix-based operating system, export your database credentials:

```bash
export DB_SOURCE=your_database_name
export DB_USERNAME=your_database_user
export DB_PASSWORD=your_database_password
```

Then, in the same shell session, open your IDEA. If you are using IntelliJ IDEA, use this command:

```bash
idea .
```

Alternatively, you can set up environment variables directly in `src/main/resources/application-dev.yml`.

### Build and Run
#### Using Maven
```bash
mvn clean install
mvn spring-boot:run
```
