package com.example.baygo.db.dto.response;

import java.math.BigDecimal;

public record ProductBuyerResponse(
        Long productId,
        Long subProductId,
        String image,
        String name,
        String description,
        double rating,
        Long quantityOfReviews,
        BigDecimal price,
        int percentOfDiscount,
        boolean inFavourite
) {
}
