package com.picsart.intership.web;

import com.picsart.intership.entity.Grade;
import com.picsart.intership.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments/v1/{assignmentId}/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Operation(
            summary = "Submit Grade",
            description = "Submits a grade for a specific assignment and student."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grade submitted successfully", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping
    public ResponseEntity<Grade> submitGrade(
            @PathVariable Long assignmentId,
            @RequestParam(name = "studentId") Long studentId,
            @RequestBody Grade grade) {
        Grade submittedGrade = gradeService.submitGrade(assignmentId, studentId, grade);
        return ResponseEntity.status(HttpStatus.CREATED).body(submittedGrade);
    }

    @Operation(
            summary = "Get Grades for Student",
            description = "Retrieves grades for a specific student."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grades retrieved successfully", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<Grade>> getGradesForStudent(@PathVariable Long studentId) {
        List<Grade> grades = gradeService.getGradesForStudent(studentId);
        return ResponseEntity.ok(grades);
    }

    @Operation(
            summary = "Get Grades for Student in Course",
            description = "Retrieves grades for a specific student in a specific course."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grades retrieved successfully", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "404", description = "Student or course not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<List<Grade>> getGradesForStudentInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        List<Grade> grades = gradeService.getGradesForStudentInCourse(studentId, courseId);
        return ResponseEntity.ok(grades);
    }

    @Operation(
            summary = "Get All Grades",
            description = "Retrieves all grades."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grades retrieved successfully", content = @Content(schema = @Schema(implementation = Grade.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<Grade>> getAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return ResponseEntity.ok(grades);
    }
}
