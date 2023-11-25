package com.picsart.intership.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class GradeDto {
    private Long studentId;
    private Long assignmentId;
    private int score;
    private String feedback;
    private LocalDate submissionDate;
}
