package com.example.coursemanagement.repository;

import com.example.coursemanagement.entity.Student;
import java.util.*;

public class StudentRepository {

    // HashMap to store students with their IDs as keys
    private final Map<Long, Student> students = new HashMap<>();
    // Variable to keep track of the current ID for students
    private Long currentId = 1L;

    // Method to save a student. If the student doesn't have an ID, assign a new one.
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(currentId++);
        }
        students.put(student.getId(), student);
        return student;
    }

    // Method to find a student by their ID
    public Student findById(Long id) {
        return students.get(id);
    }

    // Method to retrieve all students
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    // Method to delete a student by their ID
    public void delete(Long id) {
        students.remove(id);
    }
}