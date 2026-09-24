# Task Management API

A RESTful Task Management API built with **Java and Spring Boot**.
This project was developed as a backend portfolio project to practice and demonstrate REST API development, layered architecture, DTOs, validation, exception handling, pagination, sorting, and testing.

## Features

* Create, read, update, and delete tasks
* DTO-based request and response handling
* Partial task updates with `PATCH`
* Request validation
* Global exception handling
* Custom `TaskNotFoundException`
* Pagination
* Sorting
* Standardized API result classes
* Swagger / OpenAPI documentation
* Service layer unit tests
* Controller layer tests
* PostgreSQL database integration

## Technologies

* **Java**
* **Spring Boot 4**
* **Spring Web MVC**
* **Spring Data JPA**
* **Hibernate**
* **PostgreSQL**
* **Maven**
* **JUnit 5**
* **Mockito**
* **Swagger / OpenAPI**
* **Lombok**

## Project Structure

The project follows a layered architecture:

```text
src/main/java
└── spacenes._stproject
    ├── api
    │   └── controllers
    │       └── TasksController
    │
    ├── business
    │   ├── abstracts
    │   │   └── TaskService
    │   └── concretes
    │       └── TaskManager
    │
    ├── core
    │   └── utilities
    │       ├── exceptions
    │       └── results
    │
    ├── dataAccess
    │   └── abstracts
    │       └── TaskRepository
    │
    └── entities
        ├── concretes
        │   └── Task
        └── dtos
            ├── TaskRequest
            ├── TaskResponse
            └── TaskUpdateRequest
```

## API Endpoints

Base URL:

```text
/api/tasks
```

### Get All Tasks

```http
GET /api/tasks/getall
```

Returns all tasks.

### Get Task by Title

```http
GET /api/tasks/getByTitle?title=Java
```

Returns tasks matching the specified title.

### Get Tasks by Partial Title

```http
GET /api/tasks/getByTitleContains?title=Java
```

Returns tasks whose title contains the specified text.

### Create Task

```http
POST /api/tasks/createTaskWithDto
```

Example request:

```json
{
  "title": "Learn Spring Boot",
  "description": "Build a REST API with Spring Boot"
}
```

Successful response:

```text
201 Created
```

### Update Task

```http
PATCH /api/tasks/{id}
```

Example:

```json
{
  "title": "Learn Spring Boot",
  "completed": true
}
```

Only the provided fields are updated.

### Delete Task

```http
DELETE /api/tasks/{id}
```

Deletes the specified task.

### Pagination

```http
GET /api/tasks/getAllByPage?pageNo=1&pageSize=5
```

Returns tasks using pagination.

### Sorting

```http
GET /api/tasks/getAllSorted
```

Returns tasks sorted by creation date.

## Validation

Task creation requests are validated using Jakarta Bean Validation.

For example:

* Title cannot be empty.
* Title cannot exceed 50 characters.
* Description cannot exceed 250 characters.

Example of an invalid request:

```json
{
  "title": "",
  "description": "Test description"
}
```

The API returns:

```text
400 Bad Request
```

## Exception Handling

The project uses a global exception handling mechanism to provide consistent error responses.

For example, requesting a task that does not exist results in a `TaskNotFoundException`, which is handled centrally rather than inside each controller method.

## API Documentation

Swagger / OpenAPI is integrated into the project for interactive API documentation and testing.

When the application is running, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

## Database Configuration

The application uses PostgreSQL.

Before running the application, create a PostgreSQL database and configure the database connection in:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Do not commit real database credentials to GitHub.

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/echatziosman/task-management-api.git
```

### 2. Open the project

Open the project with Eclipse or another Java IDE.

### 3. Configure PostgreSQL

Create the database and update `application.properties` with your local PostgreSQL credentials.

### 4. Run the application

Run the Spring Boot application.

The API will be available at:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## Testing

The project contains basic tests for both the service and controller layers.

### Service Layer

The service tests cover scenarios such as:

* Finding tasks by title
* Handling task-not-found cases
* Creating tasks
* Updating tasks
* Partial update behavior
* Deleting tasks
* Handling deletion of non-existing tasks

### Controller Layer

Controller tests cover:

* GET requests
* POST requests
* PATCH requests
* DELETE requests
* Query parameters
* JSON responses
* HTTP status codes
* Request validation

Tests are implemented using **JUnit 5, Mockito, and Spring MVC Test**.

## Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

DTOs are used to separate API request/response models from the persistence entity.

## Project Goals

The main goals of this project were to gain practical experience with:

* Building RESTful APIs with Spring Boot
* Working with Spring Data JPA
* Designing layered backend applications
* Using DTOs
* Implementing validation
* Handling exceptions globally
* Implementing pagination and sorting
* Writing unit and controller tests
* Documenting APIs with Swagger/OpenAPI

## Future Improvements

Possible future improvements include:

* Spring Security
* JWT authentication
* User and role management
* More advanced test coverage
* Docker support
* API deployment
* Improved API response and error models

## Author

**Enes Chatzi Osman**

Computer Engineer | Java & Spring Boot Developer

GitHub: [echatziosman](https://github.com/echatziosman)
