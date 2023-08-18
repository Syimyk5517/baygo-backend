package com.example.baygo.db.dto.response;

public record ProductsInBasketResponse(
         Long sizeId,
         String description,
         String images,
         int discount,
         int cost
) {
}
