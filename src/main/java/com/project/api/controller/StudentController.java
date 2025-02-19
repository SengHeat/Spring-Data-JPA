package com.project.api.controller;

import com.project.api.model.entity.Student;
import com.project.api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentWithCourses(@PathVariable Long studentId) {
        Student student = studentService.getStudentWithCourses(studentId);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<Student> enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        Student student = studentService.enrollStudentInCourse(studentId, courseId);
        return ResponseEntity.ok(student);
    }
}
