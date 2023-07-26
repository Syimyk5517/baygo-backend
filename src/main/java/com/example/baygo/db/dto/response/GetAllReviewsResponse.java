package com.example.baygo.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class GetAllReviewsResponse {
    private Long id;
    private String image;
    private int grade;
    private String text;
    private LocalDate createAt;
}
