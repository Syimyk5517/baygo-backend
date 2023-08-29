package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ReviewResponse(
        Long reviewId,
        Long subProductId,
        String productImage,
        String productName,
        String comment,
        int grade,
        String articulOfSeller,
        int articulBG,
        String dateAndTime,
        String answer,
        List<String> reviewImages
) {
}
