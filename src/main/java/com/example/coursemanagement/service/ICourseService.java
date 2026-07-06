package com.example.coursemanagement.service;

import com.example.coursemanagement.dto.CourseDTO;
import java.util.List;

public interface ICourseService {

    // 1- Method to create a new course
    CourseDTO createCourse(CourseDTO courseDTO);

    // 2- Method to retrieve all courses
    List<CourseDTO> getAllCourses();

    // 3- Method to retrieve a course by its ID
    CourseDTO getCourseById(Long id);

    // 4- Method to update an existing course
    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

    // 5- Method to delete a course by its ID
    void deleteCourse(Long id);
}