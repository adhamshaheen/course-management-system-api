package com.example.coursemanagement.controller;

import com.example.coursemanagement.dto.StudentDTO;
import com.example.coursemanagement.service.IStudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    // Service for Student
    private final IStudentService studentService;

    // Constructor to inject the IStudentService
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    // FIRST METHOD: Endpoint to create a new student
    // Route: POST /api/students
    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    // SECOND METHOD: Endpoint to retrieve all students
    // Route: GET /api/students
    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    // THIRD METHOD: Endpoint to retrieve a student by their ID
    // Route: GET /api/students/{id}
    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // FOURTH METHOD: Endpoint to update an existing student's information
    // Route: PUT /api/students/{id}
    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(id, studentDTO);
    }

    // FIFTH METHOD: Endpoint to delete a student by their ID
    // Route: DELETE /api/students/{id}
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}