package com.picsart.intership.repo;


import com.picsart.intership.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByStatus(String status);
    List<Course> findByTeacherId(Long teacherId);
}
