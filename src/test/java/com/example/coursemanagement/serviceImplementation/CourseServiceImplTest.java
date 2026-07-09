package com.example.coursemanagement.serviceImplementation;

import com.example.coursemanagement.dto.CourseDTO;
import com.example.coursemanagement.entity.Course;
import com.example.coursemanagement.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
// Test class for CourseServiceImpl to verify its functionality
public class CourseServiceImplTest {

    @Mock
    // Mocked CourseRepository to simulate database operations
    private CourseRepository courseRepository;

    @InjectMocks
    // CourseServiceImpl instance with mocked dependencies
    private CourseServiceImpl courseService;

    // Test data for Course and CourseDTO
    private Course course;
    private CourseDTO courseDTO;

    // Method to set up test data before each test case
    @BeforeEach
    void setUp() {

        course = new Course(
                1L,
                "Spring Boot",
                "Backend development using Spring Boot",
                10L
        );


        courseDTO = new CourseDTO(
                1L,
                "Spring Boot",
                "Backend development using Spring Boot",
                10L
        );
    }


    @Test
    // Method to test the successful creation of a course
    void createCourse_ShouldCreateCourseSuccessfully() {

        // 1- Arrange
        when(courseRepository.save(any(Course.class)))
                .thenReturn(course);


        // 2- Act
        CourseDTO result = courseService.createCourse(courseDTO);


        // 3- Assert
        assertNotNull(result);
        assertEquals(course.getId(), result.getId());
        assertEquals(course.getTitle(), result.getTitle());
        assertEquals(course.getDescription(), result.getDescription());
        assertEquals(course.getInstructorId(), result.getInstructorId());

        // 4- Verify that the save method of the repository was called with any Course object
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    // Method to test the successful retrieval of all courses
    void getAllCourses_ShouldReturnCoursesSuccessfully() {

        // 1- Arrange
        List<Course> courses = new ArrayList<>();
        courses.add(course);

        // Mock the behavior of the repository to return the list of courses when findAll is called
        when(courseRepository.findAll())
                .thenReturn(courses);

        // 2- Act (Invoke the getAllCourses method of the service)
        List<CourseDTO> result = courseService.getCourses(0, 5, "id");

        // 3- Assert (Verify that the result is not null, has the expected size, and that the fields of the first course match the expected values)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(course.getId(), result.get(0).getId());
        assertEquals(course.getTitle(), result.get(0).getTitle());
        assertEquals(course.getDescription(), result.get(0).getDescription());
        assertEquals(course.getInstructorId(), result.get(0).getInstructorId());

        // 4- Verify (Verify that the findAll method of the repository was called)
        verify(courseRepository).findAll();

    }

    @Test
    // Method to test that an empty list is returned when the requested page does not exist
    void getCourses_ShouldReturnEmptyList_WhenPageIsOutOfRange() {

        // 1- Arrange
        List<Course> courses = new ArrayList<>();
        courses.add(course);

        when(courseRepository.findAll()).thenReturn(courses);

        // 2- Act
        List<CourseDTO> result = courseService.getCourses(5, 5, "id");

        // 3- Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // 4- Verify
        verify(courseRepository).findAll();
    }

    @Test
    // Method to test that courses are sorted by title successfully
    void getCourses_ShouldSortCoursesByTitleSuccessfully() {

        // 1- Arrange
        Course course1 = new Course(
                1L,
                "Spring Boot",
                "Description",
                1L
        );

        Course course2 = new Course(
                2L,
                "Algorithms",
                "Description",
                1L
        );

        Course course3 = new Course(
                3L,
                "Databases",
                "Description",
                1L
        );

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        when(courseRepository.findAll()).thenReturn(courses);

        // 2- Act
        List<CourseDTO> result = courseService.getCourses(0, 10, "title");

        // 3- Assert
        assertEquals("Algorithms", result.get(0).getTitle());
        assertEquals("Databases", result.get(1).getTitle());
        assertEquals("Spring Boot", result.get(2).getTitle());

        // 4- Verify
        verify(courseRepository).findAll();
    }

    @Test
    // Method to test the successful retrieval of a course by its ID
    void getCourseById_ShouldReturnCourseSuccessfully() {

        // 1- Arrange (Set up the test data and mock behavior)
        Long courseId = 1L;

        // Mock the behavior of the repository to return the course when searching by ID
        when(courseRepository.findById(courseId)).thenReturn(course);

        // 2- Act (Invoke the getCourseById method of the service with the course ID)
        CourseDTO result = courseService.getCourseById(courseId);

        // 3- Assert (Verify that the result is not null and that the fields of the returned CourseDTO match the expected values)
        assertNotNull(result);
        assertEquals(course.getId(), result.getId());
        assertEquals(course.getTitle(), result.getTitle());
        assertEquals(course.getDescription(), result.getDescription());
        assertEquals(course.getInstructorId(), result.getInstructorId());

        // 4- Verify (Verify that the findById method of the repository was called with the correct parameter)
        verify(courseRepository).findById(courseId);

    }

    @Test
    // Method to test the retrieval of a course by its ID when the course is not found
    void getCourseById_ShouldThrowException_WhenCourseNotFound() {

        // 1- Arrange (Set up the test data and mock behavior)
        Long courseId = 100L;

        // Mock the behavior of the repository to return null when searching for a course with the given ID
        when(courseRepository.findById(courseId)).thenReturn(null);

        // 2- Act & 3- Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    courseService.getCourseById(courseId);
                }
        );


        assertEquals(
                "Course not found with id: 100",
                exception.getMessage()
        );

        // 4- Verify (Verify that the findById method of the repository was called with the correct parameter)
        verify(courseRepository).findById(courseId);

    }

    @Test
    // Method to test the successful update of a course
    void updateCourse_ShouldUpdateCourseSuccessfully() {

        // 1- Arrange (Set up the test data and mock behavior)
        Long courseId = 1L;

        CourseDTO updatedCourseDTO = new CourseDTO(
                courseId,
                "Advanced Spring Boot",
                "Updated description",
                20L
        );

        // Mock the behavior of the repository to return the existing course when searching by ID
        when(courseRepository.findById(courseId)).thenReturn(course);

        // Mock the behavior of the repository to return the updated course when saving
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // 2- Act (Invoke the updateCourse method of the service with the course ID and updated CourseDTO)
        CourseDTO result = courseService.updateCourse(courseId, updatedCourseDTO);

        // 3- Assert (Verify that the result is not null and that the updated fields match the expected values)
        assertNotNull(result);
        assertEquals(
                "Advanced Spring Boot",
                result.getTitle()
        );
        assertEquals(
                "Updated description",
                result.getDescription()
        );

        assertEquals(
                20L,
                result.getInstructorId()
        );

        // 4- Verify (Verify that the findById and save methods of the repository were called with the correct parameters)
        verify(courseRepository).findById(courseId);
        verify(courseRepository).save(any(Course.class));

    }

    @Test
    // Method to test the update of a course when the course is not found
    void updateCourse_ShouldThrowException_WhenCourseNotFound() {

        // 1- Arrange
        Long courseId = 100L;

        // Mock the behavior of the repository to return null when searching for a course with the given ID
        when(courseRepository.findById(courseId))
                .thenReturn(null);

        // 2- Act & 3- Assert
        // Use assertThrows to check that a RuntimeException is thrown when trying to update a non-existent course
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    courseService.updateCourse(courseId, courseDTO);
                }
        );

        // Check that the exception message is as expected
        assertEquals(
                "Course not found with id: 100",
                exception.getMessage()
        );

        // 4- Verify (Verify that the findById method of the repository was called with the correct parameter and that the save method was never called)
        verify(courseRepository).findById(courseId);
        verify(courseRepository, never()).save(any(Course.class));

    }

    @Test
    // Method to test the successful deletion (soft delete) of a course
    void deleteCourse_ShouldSoftDeleteCourseSuccessfully() {

        // 1- Arrange (Set up the test data and mock behavior)
        Long courseId = 1L;

        when(courseRepository.findById(courseId))
                .thenReturn(course);

        // 2- Act (Call the deleteCourse method of the service)
        courseService.deleteCourse(courseId);
 
        // 3- Assert (Verify that the course is marked as deleted)
        assertTrue(course.isDeleted());

        // 4- Verify (Verify that the findById and save methods of the repository were called with the correct parameters)
        verify(courseRepository).findById(courseId);
        verify(courseRepository).save(course);
    }

    @Test
    // Method to test the deletion of a course when the course is not found
    void deleteCourse_ShouldThrowException_WhenCourseNotFound() {

        // 1- Arrange
        Long courseId = 100L;

        when(courseRepository.findById(courseId))
                .thenReturn(null);


        // 2- Act & 3- Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    courseService.deleteCourse(courseId);
                }
        );

        assertEquals(
                "Course not found with id: 100",
                exception.getMessage()
        );


        // 4- Verify (Verify that the findById method of the repository was called with the correct parameter and that the save method was never called)
        verify(courseRepository).findById(courseId);
        verify(courseRepository, never()).save(any(Course.class));

    }

}