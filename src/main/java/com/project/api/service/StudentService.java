package com.project.api.service;

import com.project.api.model.entity.Course;
import com.project.api.model.entity.Student;
import com.project.api.repository.CourseRepository;
import com.project.api.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // Fetch a student by ID along with their courses using the custom @Query method
    public Student getStudentWithCourses(Long studentId) {
        return studentRepository.findStudentWithCourses(studentId);
    }

    // Enroll a student in a course
    public Student enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        student.getCourses().add(course);
        course.getStudents().add(student);

        studentRepository.save(student);
        courseRepository.save(course);

        return student;
    }
}
