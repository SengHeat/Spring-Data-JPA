package com.project.api.repository;

import com.project.api.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Fetch a student by ID with the associated courses using JPQL
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.id = :studentId")
    Student findStudentWithCourses(@Param("studentId") Long studentId);
}
