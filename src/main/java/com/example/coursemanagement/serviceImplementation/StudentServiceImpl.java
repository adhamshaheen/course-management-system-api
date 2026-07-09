package com.example.coursemanagement.serviceImplementation;

import com.example.coursemanagement.dto.StudentDTO;
import com.example.coursemanagement.entity.Student;
import com.example.coursemanagement.repository.StudentRepository;
import com.example.coursemanagement.service.IStudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    // Repository for Student
    private final StudentRepository studentRepository;

    // Constructor to inject the StudentRepository
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // FIRST METHOD: Method to create a new student
    @Override
    public StudentDTO createStudent(StudentDTO dto) {

        // Check if a student with the same email already exists
        Student existingStudent = studentRepository.findByEmail(dto.getEmail());

        // If a student with the same email exists, throw an exception to prevent duplicate entries
        if (existingStudent != null) {
            throw new RuntimeException("Student with email " + dto.getEmail() + " already exists");
        }

        // Create a new Student entity from the DTO and save it to the repository
        Student student = new Student(null, dto.getName(), dto.getEmail());
        Student saved = studentRepository.save(student);
        return mapToDTO(saved);
    }

    // SECOND METHOD: Method to retrieve students with pagination and sorting
    @Override
    public List<StudentDTO> getStudents(int page, int size, String sortBy) {

        // Get all students from the repository
        List<Student> students = studentRepository.findAll();

        // Sort the students
        if (sortBy.equalsIgnoreCase("name")) {

            students.sort(
                    (student1, student2) ->
                            student1.getName().compareToIgnoreCase(student2.getName())
            );

        } else if (sortBy.equalsIgnoreCase("email")) {

            students.sort(
                    (student1, student2) ->
                            student1.getEmail().compareToIgnoreCase(student2.getEmail())
            );

        } else {

            // Default sorting by ID
            students.sort(
                    (student1, student2) ->
                            student1.getId().compareTo(student2.getId())
            );
        }

        // Calculate the start and end index for pagination
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, students.size());

        // If the requested page exceeds the number of students, return an empty list
        if (startIndex >= students.size()) {
            return new ArrayList<>();
        }

        // Get only the required page of students
        List<Student> pagedStudents = students.subList(startIndex, endIndex);

        // Convert Student objects to StudentDTO objects
        List<StudentDTO> studentDTOList = new ArrayList<>();

        for (Student student : pagedStudents) {

            StudentDTO studentDTO = mapToDTO(student);

            studentDTOList.add(studentDTO);
        }

        return studentDTOList;
    }

    // THIRD METHOD: Method to retrieve a student by their ID
    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        return mapToDTO(student);
    }

    // FOURTH METHOD: Method to update an existing student's information
    @Override
    public StudentDTO updateStudent(Long id, StudentDTO dto) {
        Student existing = studentRepository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Student not found with id: " + id);
        }

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());

        Student updated = studentRepository.save(existing);
        return mapToDTO(updated);
    }

    // FIFTH METHOD: Method to delete a student by their ID
    @Override
    public void deleteStudent(Long id) {
        studentRepository.delete(id);
    }

    // HELPER METHOD: Method to map Student entity to StudentDTO
    private StudentDTO mapToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getEmail()
        );
    }
}