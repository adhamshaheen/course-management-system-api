package com.example.coursemanagement.service;

import com.example.coursemanagement.dto.StudentDTO;
import java.util.List;

public interface IStudentService {

    // 1- Method to create a new student
    StudentDTO createStudent(StudentDTO studentDTO);

    // 2- Method to retrieve all students
    List<StudentDTO> getAllStudents();

    // 3- Method to retrieve a student by their ID
    StudentDTO getStudentById(Long id);

    // 4- Method to update an existing student's information
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    // 5- Method to delete a student by their ID
    void deleteStudent(Long id);
}