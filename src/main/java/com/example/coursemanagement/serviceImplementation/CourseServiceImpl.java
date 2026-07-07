package com.example.coursemanagement.serviceImplementation;

import com.example.coursemanagement.dto.CourseDTO;
import com.example.coursemanagement.entity.Course;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.service.ICourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {

    // Repository for Course
    private final CourseRepository courseRepository;

    // Constructor to inject the CourseRepository
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // FIRST METHOD: Method to create a new course
    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        
        Course course = new Course(
                null,
                courseDTO.getTitle(),
                courseDTO.getDescription(),
                courseDTO.getInstructorId()
        );

        Course savedCourse = courseRepository.save(course);

        return mapToDTO(savedCourse);
    }

    // SECOND METHOD: Method to retrieve all courses
    @Override
    public List<CourseDTO> getAllCourses() {

        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOList = new ArrayList<>();

        for (Course course : courses) {
            CourseDTO courseDTO = mapToDTO(course);
            courseDTOList.add(courseDTO);
        }

        return courseDTOList;
    }

    // THIRD METHOD: Method to retrieve a course by its ID
    @Override
    public CourseDTO getCourseById(Long id) {

        Course course = courseRepository.findById(id);

        if (course == null) {
            throw new RuntimeException("Course not found with id: " + id);
        }

        return mapToDTO(course);
    }

    // FOURTH METHOD: Method to update an existing course
    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {

        Course existingCourse = courseRepository.findById(id);

        if (existingCourse == null) {
            throw new RuntimeException("Course not found with id: " + id);
        }

        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setInstructorId(courseDTO.getInstructorId());

        Course updatedCourse = courseRepository.save(existingCourse);

        return mapToDTO(updatedCourse);
    }

    // FIFTH METHOD: Method to delete a course by its ID
    @Override
    public void deleteCourse(Long id) {

        Course course = courseRepository.findById(id);

        if (course == null) {
            throw new RuntimeException("Course not found with id: " + id);
        }

        // Soft delete: Mark the course as deleted instead of removing it from the repository
        course.setDeleted(true);
        courseRepository.save(course);
    }

    // HELPER METHOD: Method to map Course entity to CourseDTO
    private CourseDTO mapToDTO(Course course) {

        return new CourseDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructorId()
        );
    }
}