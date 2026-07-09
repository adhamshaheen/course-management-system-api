package com.example.coursemanagement.serviceImplementation;

import com.example.coursemanagement.dto.EnrollmentDTO;
import com.example.coursemanagement.entity.Course;
import com.example.coursemanagement.entity.Enrollment;
import com.example.coursemanagement.entity.Student;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.repository.EnrollmentRepository;
import com.example.coursemanagement.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class EnrollmentServiceImplTest {

    @Mock
    // Mocked EnrollmentRepository to simulate database operations
    private EnrollmentRepository enrollmentRepository;

    @Mock
    // Mocked StudentRepository to simulate database operations
    private StudentRepository studentRepository;

    @Mock
    // Mocked CourseRepository to simulate database operations
    private CourseRepository courseRepository;

    @InjectMocks
    // EnrollmentServiceImpl instance with mocked dependencies
    private EnrollmentServiceImpl enrollmentService;

    // Test data
    private Student student;
    private Course course;
    private Enrollment enrollment;
    private EnrollmentDTO enrollmentDTO;

    // Method to set up test data before each test case
    @BeforeEach
    void setUp() {

        student = new Student(
                1L,
                "Adham",
                "adham@gmail.com"
        );

        course = new Course(
                1L,
                "Spring Boot",
                "Backend Development",
                10L
        );

        enrollment = new Enrollment(
                1L,
                1L,
                1L,
                LocalDate.now()
        );

        enrollmentDTO = new EnrollmentDTO(
                1L,
                1L,
                1L,
                LocalDate.now()
        );
    }

    @Test
    // Method to test the successful enrollment of a student in a course
    void enrollStudent_ShouldEnrollStudentSuccessfully() {

        // 1- Arrange
        when(studentRepository.findById(1L)).thenReturn(student);

        when(courseRepository.findById(1L)).thenReturn(course);

        when(enrollmentRepository.findByStudentId(1L)).thenReturn(new ArrayList<>());

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        // 2- Act
        EnrollmentDTO result = enrollmentService.enrollStudent(enrollmentDTO);

        // 3- Assert
        assertNotNull(result);
        assertEquals(enrollment.getId(), result.getId());
        assertEquals(enrollment.getStudentId(), result.getStudentId());
        assertEquals(enrollment.getCourseId(), result.getCourseId());
        assertEquals(enrollment.getEnrollmentDate(), result.getEnrollmentDate());

        // 4- Verify
        verify(studentRepository).findById(1L);
        verify(courseRepository).findById(1L);
        verify(enrollmentRepository).findByStudentId(1L);
        verify(enrollmentRepository).save(any(Enrollment.class));

    }

    @Test
    // Method to test enrollment when the student does not exist
    void enrollStudent_ShouldThrowException_WhenStudentNotFound() {

        // 1- Arrange
        when(studentRepository.findById(1L)).thenReturn(null);

        // 2- Act & 3- Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> enrollmentService.enrollStudent(enrollmentDTO)
        );

        assertEquals(
                "Student not found with id: 1",
                exception.getMessage()
        );

        // 4- Verify
        verify(studentRepository).findById(1L);
        verify(courseRepository, never()).findById(anyLong());
        verify(enrollmentRepository, never()).save(any());

    }

    @Test
    // Method to test enrollment when the course does not exist
    void enrollStudent_ShouldThrowException_WhenCourseNotFound() {

        // 1- Arrange
        when(studentRepository.findById(1L)).thenReturn(student);

        when(courseRepository.findById(1L)).thenReturn(null);

        // 2- Act & 3- Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> enrollmentService.enrollStudent(enrollmentDTO)
        );

        assertEquals(
                "Course not found with id: 1",
                exception.getMessage()
        );

        // 4- Verify
        verify(studentRepository).findById(1L);
        verify(courseRepository).findById(1L);
        verify(enrollmentRepository, never()).save(any());

    }

    @Test
    // Method to test duplicate enrollment
    void enrollStudent_ShouldThrowException_WhenStudentAlreadyEnrolled() {

        // 1- Arrange
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);

        // Mock the behavior of the repositories to simulate existing student, course, and enrollment
        when(studentRepository.findById(1L)).thenReturn(student);

        when(courseRepository.findById(1L)).thenReturn(course);

        when(enrollmentRepository.findByStudentId(1L)).thenReturn(enrollments);

        // 2- Act & 3- Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> enrollmentService.enrollStudent(enrollmentDTO)
        );

        assertEquals(
                "Student already enrolled in this course",
                exception.getMessage()
        );

        // 4- Verify
        verify(studentRepository).findById(1L);
        verify(courseRepository).findById(1L);
        verify(enrollmentRepository).findByStudentId(1L);
        verify(enrollmentRepository, never()).save(any());

    }

    @Test
    // Method to test the successful retrieval of all enrollments
    void getAllEnrollments_ShouldReturnAllEnrollmentsSuccessfully() {

        // 1- Arrange
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);

        // Mock the behavior of the repository to return the list of enrollments
        when(enrollmentRepository.findAll()).thenReturn(enrollments);

        // 2- Act
        List<EnrollmentDTO> result = enrollmentService.getAllEnrollments();

        // 3- Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(enrollment.getId(), result.get(0).getId());
        assertEquals(enrollment.getStudentId(), result.get(0).getStudentId());
        assertEquals(enrollment.getCourseId(), result.get(0).getCourseId());

        // 4- Verify
        verify(enrollmentRepository).findAll();

    }

    @Test
    // Method to test the successful retrieval of an enrollment by its ID
    void getEnrollmentById_ShouldReturnEnrollmentSuccessfully() {

        // 1- Arrange
        // Mock the behavior of the repository to return the enrollment when searching by ID
        when(enrollmentRepository.findById(1L)).thenReturn(enrollment);

        // 2- Act
        EnrollmentDTO result = enrollmentService.getEnrollmentById(1L);

        // 3- Assert
        assertNotNull(result);
        assertEquals(enrollment.getId(), result.getId());
        assertEquals(enrollment.getStudentId(), result.getStudentId());
        assertEquals(enrollment.getCourseId(), result.getCourseId());

        // 4- Verify
        verify(enrollmentRepository).findById(1L);

    }

    @Test
    // Method to test retrieval of an enrollment when it does not exist
    void getEnrollmentById_ShouldThrowException_WhenEnrollmentNotFound() {

        // 1- Arrange
        // Mock the behavior of the repository to return null when searching for an enrollment with the given ID
        when(enrollmentRepository.findById(100L))
                .thenReturn(null);

        // 2- Act & 3- Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> enrollmentService.getEnrollmentById(100L)
        );

        assertEquals(
                "Enrollment not found with id: 100",
                exception.getMessage()
        );

        // 4- Verify
        verify(enrollmentRepository).findById(100L);

    }

    @Test
    // Method to test the successful deletion of an enrollment
    void deleteEnrollment_ShouldDeleteEnrollmentSuccessfully() {

        // 1- Arrange
        Long enrollmentId = 1L;

        // 2- Act
        enrollmentService.deleteEnrollment(enrollmentId);

        // 3- Assert
        // No assertion is needed because deleteEnrollment returns void

        // 4- Verify
        verify(enrollmentRepository).delete(enrollmentId);

    }

}