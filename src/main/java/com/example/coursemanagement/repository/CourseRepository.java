package com.example.coursemanagement.repository;

import com.example.coursemanagement.entity.Course;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class CourseRepository {
    
    // HashMap to store courses with their IDs as keys
    private final Map<Long, Course> courses = new HashMap<>();
    // Variable to keep track of the current ID for courses
    private Long currentId = 1L;

    // Method to save a course. If the course doesn't have an ID, assign a new one.
    public Course save(Course course) {
        if (course.getId() == null) {
            course.setId(currentId++);
        }
        courses.put(course.getId(), course);
        return course;
    }

    // Method to find a course by its ID
    public Course findById(Long id) {
        return courses.get(id);
    }

    // Method to retrieve all ACTIVE courses (not deleted)
    public List<Course> findAll() {
        List<Course> activeCourses = new ArrayList<>();

        for (Course course : courses.values()) {
        if (!course.isDeleted()) {
            activeCourses.add(course);
        }
    }
        return activeCourses;
    }
    
    // Method to delete a course by its ID
    public void delete(Long id) {
        courses.remove(id);
    }
}