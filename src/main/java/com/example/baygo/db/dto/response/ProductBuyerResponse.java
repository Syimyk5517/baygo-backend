package com.example.baygo.db.dto.response;

import java.math.BigDecimal;
public record ProductBuyerResponse(
     Long sizeId,
     Long subProductId,
     Long productId,
     String image,
     String name,
     String description,
     double rating,
     Long quantityOfRating,
     BigDecimal price,
     int percentOfDiscount
) {
}
