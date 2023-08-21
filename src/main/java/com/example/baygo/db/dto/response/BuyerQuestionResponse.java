package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BuyerQuestionResponse(
        Long questionId,
        Long subProductId,
        String productImage,
        String productName,
        String question,
        String answer,
        String articulOfSeller,
        int articulBG,
        String dateAndTime
) {
}
