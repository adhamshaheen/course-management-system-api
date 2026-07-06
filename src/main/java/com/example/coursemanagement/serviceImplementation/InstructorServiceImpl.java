package com.example.coursemanagement.serviceImplementation;

import com.example.coursemanagement.dto.InstructorDTO;
import com.example.coursemanagement.entity.Instructor;
import com.example.coursemanagement.repository.InstructorRepository;
import com.example.coursemanagement.service.IInstructorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorServiceImpl implements IInstructorService {

    // Repository for Instructor
    private final InstructorRepository instructorRepository;

    // Constructor to inject the InstructorRepository
    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    // FIRST METHOD: Method to create a new instructor
    @Override
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {

        Instructor instructor = new Instructor(
                null,
                instructorDTO.getName(),
                instructorDTO.getSpecialization()
        );

        Instructor savedInstructor = instructorRepository.save(instructor);

        return mapToDTO(savedInstructor);
    }

    // SECOND METHOD: Method to retrieve all instructors
    @Override
    public List<InstructorDTO> getAllInstructors() {

        List<Instructor> instructors = instructorRepository.findAll();
        List<InstructorDTO> instructorDTOList = new ArrayList<>();

        for (Instructor instructor : instructors) {
            InstructorDTO instructorDTO = mapToDTO(instructor);
            instructorDTOList.add(instructorDTO);
        }

        return instructorDTOList;
    }

    // THIRD METHOD: Method to retrieve an instructor by their ID
    @Override
    public InstructorDTO getInstructorById(Long id) {

        Instructor instructor = instructorRepository.findById(id);

        if (instructor == null) {
            throw new RuntimeException("Instructor not found with id: " + id);
        }

        return mapToDTO(instructor);
    }

    // FOURTH METHOD: Method to update an existing instructor's information
    @Override
    public InstructorDTO updateInstructor(Long id, InstructorDTO instructorDTO) {

        Instructor existingInstructor = instructorRepository.findById(id);

        if (existingInstructor == null) {
            throw new RuntimeException("Instructor not found with id: " + id);
        }

        existingInstructor.setName(instructorDTO.getName());
        existingInstructor.setSpecialization(instructorDTO.getSpecialization());

        Instructor updatedInstructor = instructorRepository.save(existingInstructor);

        return mapToDTO(updatedInstructor);
    }

    // FIFTH METHOD: Method to delete an instructor by their ID
    @Override
    public void deleteInstructor(Long id) {
        instructorRepository.delete(id);
    }

    // HELPER METHOD: Method to map Instructor entity to InstructorDTO
    private InstructorDTO mapToDTO(Instructor instructor) {

        return new InstructorDTO(
                instructor.getId(),
                instructor.getName(),
                instructor.getSpecialization()
        );
    }
}