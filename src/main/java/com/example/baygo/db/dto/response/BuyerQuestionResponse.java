package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BuyerQuestionResponse(
        Long productId,
        String buyerFullName,
        String buyerProfileImage,
        String productImage,
        String question,
        String productArticul,
        String productName,
        LocalDate dateOfQuestion
) {
}
