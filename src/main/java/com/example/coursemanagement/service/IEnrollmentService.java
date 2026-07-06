package com.example.coursemanagement.service;

import com.example.coursemanagement.dto.EnrollmentDTO;
import java.util.List;

public interface IEnrollmentService {

    // 1- Method to enroll a student in a course
    EnrollmentDTO enrollStudent(EnrollmentDTO dto);

    // 2- Method to retrieve all enrollments
    List<EnrollmentDTO> getAllEnrollments();

    // 3- Method to retrieve an enrollment by its ID
    EnrollmentDTO getEnrollmentById(Long id);

    // 4- Method to delete an enrollment by its ID
    void deleteEnrollment(Long id);
}