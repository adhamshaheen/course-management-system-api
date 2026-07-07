package com.example.coursemanagement.entity;

public class Course {

    // Course's attributes
    private Long id;
    private String title;
    private String description;
    private Long instructorId;
    // Flag to indicate if the course is deleted (soft delete)
    private boolean deleted = false;

    // Default constructor
    public Course() {}

    // Parameterized constructor
    public Course(Long id, String title, String description, Long instructorId) {
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}