package com.example.coursemanagement.dto;

import java.time.LocalDate;

public class EnrollmentDTO {

    // Enrollment's attributes
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDate enrollmentDate;

    // Default constructor
    public EnrollmentDTO() {
    }

    // Parameterized constructor
    public EnrollmentDTO(Long id, Long studentId, Long courseId, LocalDate enrollmentDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}