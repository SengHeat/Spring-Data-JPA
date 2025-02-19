package com.project.api.repository;

import com.project.api.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Fetch a course by ID with the associated students using JPQL
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.id = :courseId")
    Course findCourseWithStudents(@Param("courseId") Long courseId);


}
