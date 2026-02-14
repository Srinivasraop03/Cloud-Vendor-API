# Spring Boot Cloud Vendor API - Technical Implementation Guide

This project is a production-ready REST API designed to demonstrate core backend engineering concepts: CRUD operations, Pagination, Filtering, Validation, and Exception Handling.

## 🏗️ Architecture Overview

The project follows a standard **Layered Architecture** to ensure Separation of Concerns (SoC). This makes the code modular, testable, and maintainable.

**Flow of a Request:**
`Client (Postman/Frontend)` ➡️ **Controller** ➡️ **Service** ➡️ **Repository** ➡️ **Database**

### 1. Controller Layer
*   **Role**: The "Front Desk". It handles incoming HTTP requests (GET, POST, etc.) and returns responses. It strictly **does not** contain business logic.
*   **Key Component**: `CloudVendorController.java`
*   **Annotations Used**:
    *   `@RestController`: Marks the class as a web controller that returns data (JSON) instead of HTML views.
    *   `@RequestMapping("/cloudvendor")`: Sets the base URL path for all endpoints in this controller.
    *   `@GetMapping`, `@PostMapping`, etc.: Maps specific HTTP methods to Java methods.
    *   `@Valid`: **Crucial**. Triggers the validation rules defined in the Model (like `@NotBlank`). If validation fails, it stops execution instantly.

### 2. Service Layer
*   **Role**: The "Brain". It contains the business logic. It receives data from the Controller, processes it (calculations, validations), and asks the Repository for data.
*   **Key Component**: `CloudVendorServiceImpl.java` (Implementation of `CloudVendorService` interface).
*   **Annotations Used**:
    *   `@Service`: Tells Spring "This class holds business logic". Spring manages it as a Bean.
*   **Why an Interface?**: We use `CloudVendorService` (interface) + `CloudVendorServiceImpl` (class) to promote **Loose Coupling**. If we want to change the implementation later, we don't break the Controller code.

## Technology Stack
- **Java 17+**
- **Spring Boot 2.7.x**
- **Spring Data JPA**
- **H2 Database (In-Memory)**
- **Maven**

### 3. Repository Layer
*   **Role**: The "Librarian". It talks directly to the Database.
*   **Key Component**: `CloudVendorRepository.java`
*   **Technology**: **Spring Data JPA**.
    *   Instead of writing SQL (`SELECT * FROM...`), we extend `JpaRepository`.
    *   **Magic Methods**: Methods like `save()`, `findAll()`, and `deleteById()` are provided out-of-the-box.
    *   **Derived Queries**: We defined `findByVendorName(...)`. Spring automatically translates this into a SQL query based on the method name!
    *   **Pagination**: We pass a `Pageable` object, and Spring automatically adds `LIMIT` and `OFFSET` to the SQL.

### 4. Model/Entity Layer
*   **Role**: The "Blueprint". Defines the structure of our data.
*   **Key Component**: `CloudVendor.java`
*   **Annotations Used**:
    *   `@Entity`: Tells Hibernate "This class maps to a database table".
    *   `@Id`: Marks the primary key.
    *   `@NotBlank`: **Validation Rule**. Ensures the field cannot be null or empty.

### 5. Exception Handling
*   **Role**: The "Safety Net". Catches errors globally.
*   **Key Component**: `CloudVendorExceptionHandler.java`
*   **Annotations Used**:
    *   `@ControllerAdvice`: A global interceptor for exceptions thrown by **any** controller.
    *   `@ExceptionHandler`: Defines a method to handle a specific error (e.g., `CloudVendorNotFoundException`) and return a clean JSON response instead of a stack trace.

---

## 🚀 Key Technical Features Explained

### Pagination & Metadata
Instead of returning a simple `List` (which could be 1,000,000 requests!), we return a `Page` object.
*   **Request**: `?page=0&size=10` (Get the first 10 items).
*   **Response**: Contains the 10 items **PLUS** metadata: `totalPages`, `totalElements`, `last`, etc. This allows frontends to build "Next/Previous" buttons.

### Response Standardization
We use a `ResponseHandler` to ensure **EVERY** API response looks the same:
```json
{
    "timestamp": "2023-10-27T10:00:00.000+00:00",
    "message": "Cloud Vendor Created Successfully",
    "httpStatus": "CREATED",
    "data": { ... }
}
```
This consistency is vital for professional API clients.

---

## 🛠️ API Reference & Usage

### 1. Create a Vendor (POST)
**Endpoint**: `http://localhost:8080/cloudvendor/`
**Body**:
```json
{
  "vendorId": "C1",
  "vendorName": "Amazon",
  "vendorAddress": "USA",
  "vendorPhoneNumber": "1234567890"
}
```
*   **Test Validation**: Try sending an empty `"vendorName": ""` to see the error handler in action!

### 2. Get All Vendors (GET) - **Paginated**
**Endpoint**: `http://localhost:8080/cloudvendor/?page=0&size=5`
*   Returns a paginated list of vendors.

### 3. Filter by Name (GET)
**Endpoint**: `http://localhost:8080/cloudvendor/?vendorName=Amazon&page=0&size=5`
*   Search for specific vendors effectively.

### 4. Get Single Vendor (GET)
**Endpoint**: `http://localhost:8080/cloudvendor/C1`

### 5. Update Vendor (PUT)
**Endpoint**: `http://localhost:8080/cloudvendor/`
**Body**: JSON with updated details.

### 6. Delete Vendor (DELETE)
**Endpoint**: `http://localhost:8080/cloudvendor/C1`

---

## ⚙️ How to Run
1.  **Prerequisites**: Java 17+, Maven.
2.  **Build**: `mvn clean install`
3.  **Run**: `mvn spring-boot:run`
4.  **Swagger UI**: `http://localhost:8080/swagger-ui/`
5.  **H2 Database Console**: `http://localhost:8080/h2-console`
    *   **JDBC URL**: `jdbc:h2:mem:cloudvendordb`
    *   **User**: `sa`
    *   **Password**: `password`
