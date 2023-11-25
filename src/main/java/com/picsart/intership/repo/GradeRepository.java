package com.picsart.intership.repo;

import com.picsart.intership.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByStudentIdAndAssignment_CourseId(Long studentId, Long courseId);
}
