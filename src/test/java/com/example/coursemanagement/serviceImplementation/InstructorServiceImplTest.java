package com.example.coursemanagement.serviceImplementation;

import com.example.coursemanagement.dto.InstructorDTO;
import com.example.coursemanagement.entity.Instructor;
import com.example.coursemanagement.repository.InstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
// Test class for InstructorServiceImpl to verify its functionality
public class InstructorServiceImplTest {

    @Mock
    // Mocked InstructorRepository to simulate database operations
    private InstructorRepository instructorRepository;

    @InjectMocks
    // InstructorServiceImpl instance with mocked dependencies
    private InstructorServiceImpl instructorService;

    // Test data for Instructor and InstructorDTO
    private Instructor instructor;
    private InstructorDTO instructorDTO;

    // Method to set up test data before each test case
    @BeforeEach
    void setUp() {


        instructor = new Instructor(
                1L,
                "Dr. Ahmed",
                "Backend Development"
        );


        instructorDTO = new InstructorDTO(
                1L,
                "Dr. Ahmed",
                "Backend Development"
        );

    }


    @Test
    // Method to test the successful creation of an instructor
    void createInstructor_ShouldCreateInstructorSuccessfully() {

        // 1- Arrange
        // Mock the behavior of the repository to return the instructor when save is called
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // 2- Act
        InstructorDTO result = instructorService.createInstructor(instructorDTO);

        // 3- Assert
        assertNotNull(result);
        assertEquals(instructor.getId(), result.getId());
        assertEquals(instructor.getName(), result.getName());
        assertEquals(instructor.getSpecialization(), result.getSpecialization());

        // 4- Verify (Verify that the save method of the repository was called)
        verify(instructorRepository).save(any(Instructor.class));

    }

    @Test
    // Method to test the successful retrieval of all instructors
    void getAllInstructors_ShouldReturnInstructorsSuccessfully() {

        // 1- Arrange
        List<Instructor> instructors = new ArrayList<>();
        instructors.add(instructor);

        // Mock the behavior of the repository to return the list of instructors when findAll is called
        when(instructorRepository.findAll()).thenReturn(instructors);

        // 2- Act
        List<InstructorDTO> result =
                instructorService.getInstructors(0, 5, "id");

        // 3- Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(instructor.getId(), result.get(0).getId());
        assertEquals(instructor.getName(), result.get(0).getName());
        assertEquals(
                instructor.getSpecialization(),
                result.get(0).getSpecialization()
        );

        // 4- Verify (Verify that the findAll method of the repository was called)
        verify(instructorRepository).findAll();

    }

    @Test
    // Method to test retrieving instructors when the requested page is out of range
    void getInstructors_ShouldReturnEmptyList_WhenPageIsOutOfRange() {

        // 1- Arrange (Create a list of instructors and mock the repository to return it)
        List<Instructor> instructors = new ArrayList<>();
        instructors.add(instructor);

        when(instructorRepository.findAll()).thenReturn(instructors);

        // 2- Act (Request a page that does not exist)
        List<InstructorDTO> result = instructorService.getInstructors(5, 5, "id");

        // 3- Assert (Verify that an empty list is returned because the page is out of range)
        assertNotNull(result);
        assertEquals(0, result.size());

        // 4- Verify (Verify that the findAll method of the repository was called)
        verify(instructorRepository).findAll();

    }

    @Test
    // Method to test that instructors are sorted successfully by their names
    void getInstructors_ShouldSortInstructorsByNameSuccessfully() {

        // 1- Arrange (Create multiple instructors with different names)
        Instructor firstInstructor = new Instructor(
                1L,
                "Ahmed",
                "Backend Development"
        );

        Instructor secondInstructor = new Instructor(
                2L,
                "Mohamed",
                "Artificial Intelligence"
        );

        List<Instructor> instructors = new ArrayList<>();
        instructors.add(secondInstructor);
        instructors.add(firstInstructor);

        // Mock the repository to return the instructors list
        when(instructorRepository.findAll()).thenReturn(instructors);

        // 2- Act (Retrieve instructors sorted by name)
        List<InstructorDTO> result = instructorService.getInstructors(0, 5, "name");

        // 3- Assert (Verify that instructors are sorted alphabetically by name)
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ahmed", result.get(0).getName());
        assertEquals("Mohamed", result.get(1).getName());

        // 4- Verify (Verify that the findAll method of the repository was called)
        verify(instructorRepository).findAll();

    }

    @Test
    // Method to test the successful retrieval of an instructor by its ID
    void getInstructorById_ShouldReturnInstructorSuccessfully() {

        // 1- Arrange
        Long instructorId = 1L;

        // Mock the behavior of the repository to return the instructor when searching by ID
        when(instructorRepository.findById(instructorId)).thenReturn(instructor);

        // 2- Act
        InstructorDTO result =
                instructorService.getInstructorById(instructorId);

        // 3- Assert
        assertNotNull(result);

        assertEquals(instructor.getId(), result.getId());
        assertEquals(instructor.getName(), result.getName());
        assertEquals(
                instructor.getSpecialization(),
                result.getSpecialization()
        );

        // 4- Verify (Verify that the findById method of the repository was called with the correct parameter)
        verify(instructorRepository).findById(instructorId);

    }

    @Test
    // Method to test the retrieval of an instructor by its ID when the instructor is not found
    void getInstructorById_ShouldThrowException_WhenInstructorNotFound() {

        // 1- Arrange
        Long instructorId = 100L;

        // Mock the behavior of the repository to return null when searching for an instructor with the given ID
        when(instructorRepository.findById(instructorId)).thenReturn(null);

        // 2- Act & 3- Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    instructorService.getInstructorById(instructorId);
                }
        );

        assertEquals(
                "Instructor not found with id: 100",
                exception.getMessage()
        );

        // 4- Verify (Verify that the findById method of the repository was called)
        verify(instructorRepository).findById(instructorId);

    }

    @Test
    // Method to test the successful update of an instructor
    void updateInstructor_ShouldUpdateInstructorSuccessfully() {
        // 1- Arrange
        Long instructorId = 1L;

        InstructorDTO updatedInstructorDTO = new InstructorDTO(
                instructorId,
                "Dr. Mohamed",
                "Spring Boot Development"
        );

        // Mock the behavior of the repository to return the existing instructor when searching by ID
        when(instructorRepository.findById(instructorId))
                .thenReturn(instructor);

        // Mock the behavior of the repository to return the updated instructor when saving
        when(instructorRepository.save(any(Instructor.class)))
                .thenReturn(instructor);

        // 2- Act
        InstructorDTO result =
                instructorService.updateInstructor(
                        instructorId,
                        updatedInstructorDTO
                );

        // 3- Assert
        assertNotNull(result);

        assertEquals(
                "Dr. Mohamed",
                result.getName()
        );

        assertEquals(
                "Spring Boot Development",
                result.getSpecialization()
        );

        // 4- Verify (Verify that findById and save methods were called)
        verify(instructorRepository).findById(instructorId);
        verify(instructorRepository).save(any(Instructor.class));

    }

    @Test
    // Method to test the update of an instructor when the instructor is not found
    void updateInstructor_ShouldThrowException_WhenInstructorNotFound() {
        // 1- Arrange
        Long instructorId = 100L;

        // Mock the behavior of the repository to return null when searching by ID
        when(instructorRepository.findById(instructorId)).thenReturn(null);

        // 2- Act & 3- Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    instructorService.updateInstructor(
                            instructorId,
                            instructorDTO
                    );
                }
        );

        assertEquals(
                "Instructor not found with id: 100",
                exception.getMessage()
        );

        // 4- Verify (Verify that findById was called and save was never called)
        verify(instructorRepository).findById(instructorId);
        verify(instructorRepository, never()).save(any(Instructor.class));

    }

    @Test
    // Method to test the successful deletion of an instructor
    void deleteInstructor_ShouldDeleteInstructorSuccessfully() {
        // 1- Arrange
        Long instructorId = 1L;

        // 2- Act
        instructorService.deleteInstructor(instructorId);

        // 3- Assert
        // No assertion is needed because deleteInstructor returns void

        // 4- Verify (Verify that the delete method of the repository was called with the correct ID)
        verify(instructorRepository).delete(instructorId);

    }

}