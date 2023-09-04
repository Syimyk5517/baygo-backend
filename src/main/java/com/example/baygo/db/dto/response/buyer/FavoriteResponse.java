package com.example.baygo.db.dto.response.buyer;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record FavoriteResponse(
        Long id,
        String photo,
        String productName,
        double rating,
        int amountOfLike,
        BigDecimal price
) {
}
