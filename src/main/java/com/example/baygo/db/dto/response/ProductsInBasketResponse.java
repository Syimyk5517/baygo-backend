package com.example.baygo.db.dto.response;

import java.math.BigDecimal;

public record ProductsInBasketResponse(
        Long sizeId,
        String description,
        String size,
        String image,
        int discount,
        BigDecimal cost,
        int productQuantity
) {
}
