package com.example.coursemanagement.service;

import com.example.coursemanagement.dto.InstructorDTO;
import java.util.List;

public interface IInstructorService {

    // 1- Method to create a new instructor
    InstructorDTO createInstructor(InstructorDTO instructorDTO);

    // 2- Method to retrieve all instructors
    List<InstructorDTO> getAllInstructors();

    // 3- Method to retrieve an instructor by their ID
    InstructorDTO getInstructorById(Long id);

    // 4- Method to update an existing instructor's information
    InstructorDTO updateInstructor(Long id, InstructorDTO instructorDTO);

    // 5- Method to delete an instructor by their ID
    void deleteInstructor(Long id);
}