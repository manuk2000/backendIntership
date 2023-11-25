package com.picsart.intership.service;

import com.picsart.intership.entity.Assignment;
import com.picsart.intership.entity.Course;
import com.picsart.intership.repo.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAssignmentsForCourse(Long courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }

    public Assignment createAssignment(Long courseId, Assignment assignment) {
        assignment.setCourse(new Course().setId(courseId));
        return assignmentRepository.save(assignment);
    }

    public boolean updateAssignment(Long id, Assignment updatedAssignment) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(id);
        if (optionalAssignment.isPresent()) {
            Assignment assignment = optionalAssignment.get();
            assignment.setTitle(updatedAssignment.getTitle());
            assignment.setContent(updatedAssignment.getContent());
            assignmentRepository.save(assignment);
            return true;
        }
        return false;
    }

    public boolean deleteAssignment(Long id) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(id);
        if (optionalAssignment.isPresent()) {
            Assignment assignment = optionalAssignment.get();
            assignmentRepository.delete(assignment);
            return true;
        }
        return false;
    }
}
