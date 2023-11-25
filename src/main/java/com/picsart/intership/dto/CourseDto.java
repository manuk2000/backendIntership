package com.picsart.intership.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class CourseDto {
    private String title;
    private String description;
    private Long teacherId;
    private LocalDate startDate;
    private int credits;
    private int enrollmentLimit;
    private String status;
}
