package com.picsart.intership.web;

import com.picsart.intership.entity.Course;
import com.picsart.intership.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/v1")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(
            summary = "Get All Courses",
            description = "Returns a list of all courses with the ability to filter by status and teacher ID."
    )
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "teacherId", required = false) Long teacherId) {

        List<Course> courses = courseService.getAllCourses(status, teacherId);
        return ResponseEntity.ok(courses);
    }

    @Operation(
            summary = "Get Single Course",
            description = "Returns information about a specific course based on its identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course information", content = @Content(schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Create Course",
            description = "Creates a new course."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created successfully", content = @Content(schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @Operation(
            summary = "Update Course",
            description = "Updates information about a specific course based on its identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course information updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        boolean success = courseService.updateCourse(id, updatedCourse);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Delete Course",
            description = "Deletes a specific course based on its identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        boolean success = courseService.deleteCourse(id);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


