package com.picsart.intership.web;

import com.picsart.intership.entity.Assignment;
import com.picsart.intership.service.AssignmentService;
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
@RequestMapping("/api/courses/v1/{courseId}/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Operation(
            summary = "Get Assignments for Course",
            description = "Retrieves a list of assignments for the specified course."
    )
    @GetMapping
    public ResponseEntity<List<Assignment>> getAssignmentsForCourse(@PathVariable Long courseId) {
        List<Assignment> assignments = assignmentService.getAssignmentsForCourse(courseId);
        return ResponseEntity.ok(assignments);
    }

    @Operation(
            summary = "Create Assignment",
            description = "Creates a new assignment for the specified course."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Assignment created successfully", content = @Content(schema = @Schema(implementation = Assignment.class))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@PathVariable Long courseId, @RequestBody Assignment assignment) {
        Assignment createdAssignment = assignmentService.createAssignment(courseId, assignment);
        if (createdAssignment != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Update Assignment",
            description = "Updates the details of the specified assignment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assignment updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Assignment not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAssignment(@PathVariable Long id, @RequestBody Assignment updatedAssignment) {
        boolean success = assignmentService.updateAssignment(id, updatedAssignment);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Delete Assignment",
            description = "Deletes the specified assignment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assignment deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Assignment not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        boolean success = assignmentService.deleteAssignment(id);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
