package com.example.coursemanagement.serviceImplementation;

import com.example.coursemanagement.dto.EnrollmentDTO;
import com.example.coursemanagement.entity.Enrollment;
import com.example.coursemanagement.entity.Student;
import com.example.coursemanagement.entity.Course;
import com.example.coursemanagement.repository.EnrollmentRepository;
import com.example.coursemanagement.repository.StudentRepository;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.service.IEnrollmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {

    // Repositories for Enrollment, Student, and Course
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // Constructor to inject the repositories
    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // FIRST METHOD: Method to enroll a student in a course
    @Override
    public EnrollmentDTO enrollStudent(EnrollmentDTO dto) {

        // 1. Validate student
        Student student = studentRepository.findById(dto.getStudentId());
        if (student == null) {
            throw new RuntimeException("Student not found with id: " + dto.getStudentId());
        }

        // 2. Validate course
        Course course = courseRepository.findById(dto.getCourseId());
        if (course == null) {
            throw new RuntimeException("Course not found with id: " + dto.getCourseId());
        }

        // 3. Prevent duplicate enrollment
        List<Enrollment> existingEnrollments =
                enrollmentRepository.findByStudentId(dto.getStudentId());

        for (Enrollment e : existingEnrollments) {
            if (e.getCourseId().equals(dto.getCourseId())) {
                throw new RuntimeException("Student already enrolled in this course");
            }
        }

        // 4. Create enrollment
        Enrollment enrollment = new Enrollment(
                null,
                dto.getStudentId(),
                dto.getCourseId(),
                LocalDate.now()
        );

        // 5. Save enrollment and assign an ID using the save method of the EnrollmentRepository
        Enrollment saved = enrollmentRepository.save(enrollment);

        return mapToDTO(saved);
    }

    // SECOND METHOD: Method to retrieve all enrollments
    @Override
    public List<EnrollmentDTO> getAllEnrollments() {

        List<Enrollment> enrollments = enrollmentRepository.findAll();
        List<EnrollmentDTO> enrollmentDTOs = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            EnrollmentDTO enrollmentDTO = mapToDTO(enrollment);
            enrollmentDTOs.add(enrollmentDTO);
        }

        return enrollmentDTOs;
    }

    // THIRD METHOD: Method to retrieve an enrollment by its ID
    @Override
    public EnrollmentDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id);
        if (enrollment == null) {
            throw new RuntimeException("Enrollment not found with id: " + id);
        }
        return mapToDTO(enrollment);
    }

    // FOURTH METHOD: Method to delete an enrollment
    @Override
    public void deleteEnrollment(Long id) {
        enrollmentRepository.delete(id);
    }

    // HELPER METHOD: Method to map Enrollment entity to EnrollmentDTO
    private EnrollmentDTO mapToDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudentId(),
                enrollment.getCourseId(),
                enrollment.getEnrollmentDate()
        );
    }
}