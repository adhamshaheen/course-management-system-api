package com.example.coursemanagement.controller;

import com.example.coursemanagement.dto.EnrollmentDTO;
import com.example.coursemanagement.service.IEnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final IEnrollmentService enrollmentService;

    public EnrollmentController(IEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public EnrollmentDTO enrollStudent(@RequestBody EnrollmentDTO enrollmentDTO) {
        return enrollmentService.enrollStudent(enrollmentDTO);
    }

    @GetMapping
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/{id}")
    public EnrollmentDTO getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
    }
}