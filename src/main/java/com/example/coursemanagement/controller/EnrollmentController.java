package com.example.coursemanagement.controller;

import com.example.coursemanagement.dto.EnrollmentDTO;
import com.example.coursemanagement.service.IEnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    // Service for Enrollment
    private final IEnrollmentService enrollmentService;

    // Constructor to inject the IEnrollmentService
    public EnrollmentController(IEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // FIRST METHOD: Endpoint to enroll a student in a course
    // Route: POST /api/enrollments
    @PostMapping
    public EnrollmentDTO enrollStudent(@RequestBody EnrollmentDTO enrollmentDTO) {
        return enrollmentService.enrollStudent(enrollmentDTO);
    }

    // SECOND METHOD: Endpoint to retrieve all enrollments
    // Route: GET /api/enrollments
    @GetMapping
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    // THIRD METHOD: Endpoint to retrieve an enrollment by its ID
    // Route: GET /api/enrollments/{id}
    @GetMapping("/{id}")
    public EnrollmentDTO getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    // FOURTH METHOD: Endpoint to update an existing enrollment's information
    // Route: PUT /api/enrollments/{id}
    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
    }
}