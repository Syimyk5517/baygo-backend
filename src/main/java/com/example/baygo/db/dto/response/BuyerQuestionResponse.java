package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BuyerQuestionResponse(
        String productImage,
        String question,
        String productArticul,
        String productName,
        LocalDate dateOfQuestion
) {
}
