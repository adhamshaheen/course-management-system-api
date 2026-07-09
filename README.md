# Course Management API

A Spring Boot REST API for managing students, instructors, courses, and course enrollments.
This project was developed as part of the Innovera Internship to practice backend development concepts using Spring Boot, layered architecture, REST APIs, unit testing, and software design principles.

---

## Project Overview

The Course Management API provides a complete backend system for managing:

* Students
* Instructors
* Courses
* Student enrollments in courses

The application follows a layered architecture:

```
Controller Layer
        |
Service Layer
        |
Repository Layer
        |
Data Storage
```

Each layer has a specific responsibility to keep the code organized, maintainable, and scalable.

---

# Technologies Used

* Java 21
* Spring Boot 3.5.16
* Maven
* JUnit 5
* Mockito
* REST APIs
* Postman for API testing

---

# Project Structure

```
src/main/java/com/example/coursemanagement

├── controller
│   ├── StudentController
│   ├── InstructorController
│   ├── CourseController
│   └── EnrollmentController
│
├── service
│   ├── IStudentService
│   ├── IInstructorService
│   ├── ICourseService
│   └── IEnrollmentService
│
├── serviceImplementation
│   ├── StudentServiceImpl
│   ├── InstructorServiceImpl
│   ├── CourseServiceImpl
│   └── EnrollmentServiceImpl
│
├── repository
│   ├── StudentRepository
│   ├── InstructorRepository
│   ├── CourseRepository
│   └── EnrollmentRepository
│
├── entity
│   ├── Student
│   ├── Instructor
│   ├── Course
│   └── Enrollment
│
└── dto
    ├── StudentDTO
    ├── InstructorDTO
    ├── CourseDTO
    └── EnrollmentDTO
```

---

# Features Implemented

## Student Management

The API supports:

* Create student
* Retrieve all students
* Retrieve student by ID
* Update student information
* Delete student
* Prevent duplicate students using email validation

---

## Instructor Management

The API supports:

* Create instructor
* Retrieve all instructors
* Retrieve instructor by ID
* Update instructor information
* Delete instructor

---

## Course Management

The API supports:

* Create course
* Retrieve courses
* Retrieve course by ID
* Update course information
* Soft delete courses

### Soft Delete

Instead of permanently removing a course, the course is marked as deleted while keeping the data available.

---

## Enrollment Management

The API supports:

* Enroll students into courses
* Retrieve enrollments
* Retrieve enrollment by ID
* Delete enrollment

Additional validation:

* Student existence validation
* Course existence validation
* Prevent duplicate enrollments

---

# Pagination and Sorting

Pagination and sorting were implemented for retrieving data.

Example:

```
GET /api/students?page=0&size=5&sortBy=name
```

Parameters:

| Parameter | Description                |
| --------- | -------------------------- |
| page      | Page number                |
| size      | Number of records per page |
| sortBy    | Sorting field              |

Supported sorting examples:

```
id
name
title
```

---

# Unit Testing

The service layer was tested using:

* JUnit 5
* Mockito

Tests cover:

* Successful operations
* Exception handling
* Repository interactions
* Soft delete functionality
* Pagination and sorting behavior

Example tested services:

```
StudentServiceImplTest
CourseServiceImplTest
InstructorServiceImplTest
EnrollmentServiceImplTest
```

---

# Running the Project

## Prerequisites

Make sure you have:

* Java 21 installed
* Maven installed

Check versions:

```
java -version

mvn -version
```

---

## Run the Application

Clone the repository:

```
git clone <repository-url>
```

Navigate to the project folder:

```
cd coursemanagement
```

Run the application:

```
mvn spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

# Testing APIs

The APIs can be tested using Postman.

Example:

## Create Student

POST

```
http://localhost:8080/api/students
```

Request Body:

```json
{
    "name": "Ahmed Ali",
    "email": "ahmed@gmail.com"
}
```

Response:

```json
{
    "id": 1,
    "name": "Ahmed Ali",
    "email": "ahmed@gmail.com"
}
```

---

# Future Improvements

Possible improvements:

* Replace HashMap repositories with a real database using Spring Data JPA
* Add authentication and authorization using Spring Security
* Add global exception handling
* Add API documentation using Swagger/OpenAPI
* Add Docker support

---

# Author

Adham Shaheen

Computer Engineering Student
German University in Cairo (GUC)
