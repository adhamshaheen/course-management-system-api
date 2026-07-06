package com.example.coursemanagement.dto;

public class CourseDTO {

    // Course's attributes
    private Long id;
    private String title;
    private String description;
    private Long instructorId;

    // Default constructor
    public CourseDTO() {
    }

    // Parameterized constructor
    public CourseDTO(Long id, String title, String description, Long instructorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
}