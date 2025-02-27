package com.project.api.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.api.model.entity.Student;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToMany(mappedBy = "courses") // Mapped by Student entity
    @JsonBackReference // Prevents circular references by managing the "back" side
    private Set<Student> students = new HashSet<>();

    // Constructors
    public Course() {}
    public Course(String title) {
        this.title = title;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Set<Student> getStudents() { return students; }
    public void setStudents(Set<Student> students) { this.students = students; }
}
