package com.example.coursemanagement.repository;

import com.example.coursemanagement.entity.Instructor;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class InstructorRepository {

    // HashaMap to store instructors in memory
    private final Map<Long, Instructor> instructors = new HashMap<>();
    // Variable to keep track of the current ID for instructors
    private Long currentId = 1L;

    // Method to save an instructor. If the instructor doesn't have an ID, assign a new one.
    public Instructor save(Instructor instructor) {
        if (instructor.getId() == null) {
            instructor.setId(currentId++);
        }
        instructors.put(instructor.getId(), instructor);
        return instructor;
    }

    // Method to find an instructor by their ID
    public Instructor findById(Long id) {
        return instructors.get(id);
    }

    // Method to retrieve all instructors
    public List<Instructor> findAll() {
        return new ArrayList<>(instructors.values());
    }

    // Method to delete an instructor by their ID
    public void delete(Long id) {
        instructors.remove(id);
    }
}