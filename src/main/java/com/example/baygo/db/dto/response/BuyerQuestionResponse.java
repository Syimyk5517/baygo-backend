package com.example.baygo.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class BuyerQuestionResponse {
    private Long id;
    private String productPhoto;
    private String description;
    private LocalDate createAt;
}
