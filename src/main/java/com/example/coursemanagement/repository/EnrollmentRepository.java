package com.example.coursemanagement.repository;

import com.example.coursemanagement.entity.Enrollment;
import java.util.*;

public class EnrollmentRepository {

    // HashMap to store enrollments with the enrollment ID as the key
    private final Map<Long, Enrollment> enrollments = new HashMap<>();
    // Variable to keep track of the current ID for enrollments
    private Long currentId = 1L;

    // Method to save an enrollment. If the enrollment doesn't have an ID, assign a new one.
    public Enrollment save(Enrollment enrollment) {
        if (enrollment.getId() == null) {
            enrollment.setId(currentId++);
        }
        enrollments.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    // Method to find an enrollment by its ID
    public Enrollment findById(Long id) {
        return enrollments.get(id);
    }

    // Method to retrieve all enrollments
    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments.values());
    }

    // Method to delete an enrollment by its ID
    public void delete(Long id) {
        enrollments.remove(id);
    }

    // Method to find a student's enrollments by their student ID
    public List<Enrollment> findByStudentId(Long studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments.values()) {
            if (e.getStudentId().equals(studentId)) {
                result.add(e);
            }
        }
        return result;
    }

    // Method to find all enrollments for a specific course by its course ID
    public List<Enrollment> findByCourseId(Long courseId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments.values()) {
            if (e.getCourseId().equals(courseId)) {
                result.add(e);
            }
        }
        return result;
    }
}