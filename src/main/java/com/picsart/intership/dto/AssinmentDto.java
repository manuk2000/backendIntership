package com.picsart.intership.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class AssinmentDto {
    private String title;
    private String content;
    private LocalDate dueDate;
    private Long courseId;
    private LocalDate postedDate;
    private int maxScore;
    private String submissionFormat;

}
