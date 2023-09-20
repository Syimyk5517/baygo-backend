package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record DiscountProductResponse(
        Long productId,
        Long subProductId,
        String image,
        String articulOfSeller,
        int articluBG,
        String item,
        String brand,
        int rating,
        String color,
        int percent
) {
}
