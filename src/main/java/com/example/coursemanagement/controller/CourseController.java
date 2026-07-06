package com.example.coursemanagement.controller;

import com.example.coursemanagement.dto.CourseDTO;
import com.example.coursemanagement.service.ICourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    // Service for Course
    private final ICourseService courseService;

    // Constructor to inject the ICourseService
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    // FIRST METHOD: Endpoint to create a new course
    // Route: POST /api/courses
    @PostMapping
    public CourseDTO createCourse(@RequestBody CourseDTO courseDTO) {
        return courseService.createCourse(courseDTO);
    }

    // SECOND METHOD: Endpoint to retrieve all courses
    // Route: GET /api/courses
    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    // THIRD METHOD: Endpoint to retrieve a course by its ID
    // Route: GET /api/courses/{id}
    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    // FOURTH METHOD: Endpoint to update an existing course's information
    // Route: PUT /api/courses/{id}
    @PutMapping("/{id}")
    public CourseDTO updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        return courseService.updateCourse(id, courseDTO);
    }

    // FIFTH METHOD: Endpoint to delete a course by its ID
    // Route: DELETE /api/courses/{id}
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }
}