package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record ReviewResponse(
        Long id,
        String reviewImage,
        String productName,
        String articul,
        String subCategory,
        String productBrand,
        int grade,
        String dateTime
) {
}
