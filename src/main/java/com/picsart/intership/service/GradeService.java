package com.picsart.intership.service;

import com.picsart.intership.entity.Assignment;
import com.picsart.intership.entity.Grade;
import com.picsart.intership.entity.UserProfile;
import com.picsart.intership.repo.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UserProfileService userProfileService;


    public Grade submitGrade(Long assignmentId, Long studentId, Grade grade) {

        grade.setAssignment(new Assignment().setId(assignmentId));
        grade.setStudent(new UserProfile().setId(studentId));
        return gradeRepository.save(grade);
    }

    public List<Grade> getGradesForStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getGradesForStudentInCourse(Long studentId, Long courseId) {
        gradeRepository.findByStudentIdAndAssignment_CourseId(studentId, courseId);
        return null;
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }
}
