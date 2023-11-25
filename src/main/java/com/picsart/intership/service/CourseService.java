package com.picsart.intership.service;

import com.picsart.intership.entity.Course;
import com.picsart.intership.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses(String status, Long teacherId) {
        List<Course> courses;
        if (status != null) {
            courses = getCoursesByStatus(status);
        } else if (teacherId != null) {
            courses = getCoursesByTeacherId(teacherId);
        } else {
            courses = getAllCourses();
        }
        return courses;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByStatus(String status) {
        return courseRepository.findByStatus(status);
    }

    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public boolean updateCourse(Long id, Course updatedCourse) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setDescription(updatedCourse.getDescription());
            courseRepository.save(existingCourse);
            return true;
        }
        return false;
    }

    public boolean deleteCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            courseRepository.delete(course);
            return true;
        }
        return false;
    }
}
