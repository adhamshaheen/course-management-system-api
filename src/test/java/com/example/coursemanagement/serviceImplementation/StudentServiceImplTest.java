package com.example.coursemanagement.serviceImplementation;

import com.example.coursemanagement.dto.StudentDTO;
import com.example.coursemanagement.entity.Student;
import com.example.coursemanagement.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

// Initialize the test class with MockitoExtension to enable Mockito annotations
@ExtendWith(MockitoExtension.class)
// Test class for StudentServiceImpl
public class StudentServiceImplTest {

    @Mock
    // Mock the StudentRepository to simulate its behavior in tests
    private StudentRepository studentRepository;

    @InjectMocks
    // Inject the mocked StudentRepository into the StudentServiceImpl instance for testing
    private StudentServiceImpl studentService;

    // Variables to hold test data for Student and StudentDTO
    private Student student;
    private StudentDTO studentDTO;

    @BeforeEach
    // Method to set up test data before each test case
    void setUp() {

        student = new Student(
                1L,
                "Adham",
                "adham@gmail.com"
        );

        studentDTO = new StudentDTO(
                1L,
                "Adham",
                "adham@gmail.com"
        );
    }

    @Test
    // Test method to verify that a student is created successfully
    void createStudent_ShouldCreateStudentSuccessfully() {

        // 1- Arrange (Arrange the necessary preconditions and inputs for the test)
        when(studentRepository.findByEmail(studentDTO.getEmail()))
                .thenReturn(null);

        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);

        // 2- Act (Act on the object or method under test)
        StudentDTO result = studentService.createStudent(studentDTO);

        // 3- Assert (Assert that the expected results have occurred)
        assertNotNull(result);
        assertEquals(student.getId(), result.getId());
        assertEquals(student.getName(), result.getName());
        assertEquals(student.getEmail(), result.getEmail());

        // 4- Verify (Verify that the repository methods were called with the expected arguments)
        verify(studentRepository).findByEmail(studentDTO.getEmail());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    // Test method to verify that an exception is thrown when trying to create a student with an existing email
    void createStudent_ShouldThrowException_WhenEmailAlreadyExists() {

        // Arrange (Arrange the necessary preconditions and inputs for the test)
        when(studentRepository.findByEmail(studentDTO.getEmail()))
                .thenReturn(student);

        // Act & Assert (Act on the object or method under test and assert that the expected exception is thrown)
        // Execute studentService.createStudent(studentDTO) and make sure it throws a RuntimeException.
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> studentService.createStudent(studentDTO)
        );

        assertEquals(
                "Student with email adham@gmail.com already exists",
                exception.getMessage()
        );

        verify(studentRepository).findByEmail(studentDTO.getEmail());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    // Test method to verify that all students are retrieved successfully
    void getAllStudents_ShouldReturnAllStudentsSuccessfully() {

        // 1- Arrange (Create a list of students and mock the repository to return it)
        List<Student> students = new ArrayList<>();
        students.add(student);

        when(studentRepository.findAll())
                .thenReturn(students);


        // 2- Act (Invoke the method under test to retrieve all students)
        List<StudentDTO> result = studentService.getAllStudents();


        // 3- Assert (Check that the result is not null, has the expected size, and contains the expected student data)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(student.getId(), result.get(0).getId());
        assertEquals(student.getName(), result.get(0).getName());
        assertEquals(student.getEmail(), result.get(0).getEmail());

        // 4- Verify (Verify that the repository's findAll method was called)
        verify(studentRepository).findAll();
    }

    @Test
    // Test method to verify that a student is retrieved successfully by their ID
    void getStudentById_ShouldReturnStudentSuccessfully() {

        // 1- Arrange (Arrange the necessary preconditions and inputs for the test)
        Long studentId = 1L;

        when(studentRepository.findById(studentId))
                .thenReturn(student);


        // 2- Act (Invoke the method under test to retrieve a student by their ID)
        StudentDTO result = studentService.getStudentById(studentId);


        // 3- Assert (Assert that the result is not null and contains the expected student data)
        assertNotNull(result);
        assertEquals(student.getId(), result.getId());
        assertEquals(student.getName(), result.getName());
        assertEquals(student.getEmail(), result.getEmail());

        // 4- Verify (Verify that the repository's findById method was called with the expected student ID)
        verify(studentRepository).findById(studentId);
    }

    @Test
    // Test method to verify that an exception is thrown when trying to retrieve a student by a non-existing ID
    void getStudentById_ShouldThrowException_WhenStudentNotFound() {

        // 1- Arrange (Arrange the necessary preconditions and inputs for the test)
        Long studentId = 100L; // Use a non-existing student ID for testing

        // Mock the repository to return null when searching for a student with the given ID
        when(studentRepository.findById(studentId))
                .thenReturn(null);


        // 2,3- Act & Assert (Act on the object or method under test and assert that the expected exception is thrown)
        // Execute studentService.getStudentById(studentId) and make sure it throws a RuntimeException.
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    studentService.getStudentById(studentId);
                }
        );

        assertEquals(
                "Student not found with id: 100",
                exception.getMessage()
        );

        assertEquals(
                "Student not found with id: 100",
                exception.getMessage()
        );

        // 4- Verify (Verify that the repository's findById method was called with the expected student ID)
        verify(studentRepository).findById(studentId);
    }

    @Test
    // Test method to verify that a student's information is updated successfully
    void updateStudent_ShouldUpdateStudentSuccessfully() {

        // 1- Arrange (Arrange the necessary preconditions and inputs for the test)
        Long studentId = 1L;

        // Create a new StudentDTO with updated information for the student
        StudentDTO updatedStudentDTO = new StudentDTO(
                studentId,
                "Updated Adham",
                "updated@gmail.com"
        );

        // Mock the repository to return the existing student when searching by ID
        when(studentRepository.findById(studentId))
                .thenReturn(student);

        // Mock the repository to return the updated student when saving the changes
        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);


        // 2- Act (Invoke the method under test to update the student's information)
        StudentDTO result = studentService.updateStudent(studentId, updatedStudentDTO);


        // 3- Assert (Assert that the result is not null and contains the updated student information)
        assertNotNull(result);
        assertEquals("Updated Adham", result.getName());
        assertEquals("updated@gmail.com", result.getEmail());

        // 4- Verify (Verify that the repository's findById and save methods were called with the expected arguments)
        verify(studentRepository).findById(studentId);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    // Test method to verify that an exception is thrown when trying to update a student with a non-existing ID
    void updateStudent_ShouldThrowException_WhenStudentNotFound() {

        // 1- Arrange (Arrange the necessary preconditions and inputs for the test)
        Long studentId = 100L; // Use a non-existing student ID for testing

        // Create a new StudentDTO with updated information for the student
        StudentDTO updatedStudentDTO = new StudentDTO(
                studentId,
                "Updated Name",
                "updated@gmail.com"
        );

        // Mock the repository to return null when searching for a student with the given ID
        when(studentRepository.findById(studentId))
                .thenReturn(null);


        // 2,3- Act & Assert
        // Execute studentService.updateStudent(studentId, updatedStudentDTO) and make sure it throws a RuntimeException.
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    studentService.updateStudent(studentId, updatedStudentDTO);
                }
        );

        assertEquals(
                "Student not found with id: 100",
                exception.getMessage()
        );

        // 4- Verify (Verify that the repository's findById method was called with the expected student ID and that the save method was never called)
        verify(studentRepository).findById(studentId);
        verify(studentRepository, never())
                .save(any(Student.class));
    }

    @Test
    // Test method to verify that a student is deleted successfully by their ID
    void deleteStudent_ShouldDeleteStudentSuccessfully() {

        // 1- Arrange (Arrange the necessary preconditions and inputs for the test)
        Long studentId = 1L;


        // 2- Act (Invoke the method under test to delete the student by their ID)
        studentService.deleteStudent(studentId);


        // 3- Assert (Verify that the repository's delete method was called with the expected student ID)
        verify(studentRepository).delete(studentId);
    }
}
