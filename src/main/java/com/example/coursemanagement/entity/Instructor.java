package com.example.coursemanagement.entity;

public class Instructor {

    // Instructor's attributes
    private Long id;
    private String name;
    private String specialization;

    // Default constructor
    public Instructor() {}

    // Parameterized constructor
    public Instructor(Long id, String name, String specialization) {
        this.id = id;
        this.specialization = specialization;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}