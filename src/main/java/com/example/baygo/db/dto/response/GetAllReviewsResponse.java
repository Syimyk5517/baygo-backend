package com.example.baygo.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class GetAllReviewsResponse {
    private Long reviewId;
    private String productImage;
    private int grade;
    private String comment;
    private LocalDate dateAndTime;
}
