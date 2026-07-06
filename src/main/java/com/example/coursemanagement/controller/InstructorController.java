package com.example.coursemanagement.controller;

import com.example.coursemanagement.dto.InstructorDTO;
import com.example.coursemanagement.service.IInstructorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    // Service for Instructor
    private final IInstructorService instructorService;

    // Constructor to inject the IInstructorService
    public InstructorController(IInstructorService instructorService) {
        this.instructorService = instructorService;
    }

    // FIRST METHOD: Endpoint to create a new instructor
    // Route: POST /api/instructors
    @PostMapping
    public InstructorDTO createInstructor(@RequestBody InstructorDTO instructorDTO) {
        return instructorService.createInstructor(instructorDTO);
    }

    // SECOND METHOD: Endpoint to retrieve all instructors
    // Route: GET /api/instructors
    @GetMapping
    public List<InstructorDTO> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    // THIRD METHOD: Endpoint to retrieve an instructor by their ID
    // Route: GET /api/instructors/{id}
    @GetMapping("/{id}")
    public InstructorDTO getInstructorById(@PathVariable Long id) {
        return instructorService.getInstructorById(id);
    }

    // FOURTH METHOD: Endpoint to update an existing instructor's information
    // Route: PUT /api/instructors/{id}
    @PutMapping("/{id}")
    public InstructorDTO updateInstructor(@PathVariable Long id, @RequestBody InstructorDTO instructorDTO) {
        return instructorService.updateInstructor(id, instructorDTO);
    }

    // FIFTH METHOD: Endpoint to delete an instructor by their ID
    // Route: DELETE /api/instructors/{id}
    @DeleteMapping("/{id}")
    public void deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
    }
}