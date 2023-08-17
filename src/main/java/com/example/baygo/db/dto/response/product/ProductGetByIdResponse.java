package com.example.baygo.db.dto.response.product;

import com.example.baygo.db.dto.response.ColorResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductGetByIdResponse(
        Long productId,
        Long suProductId,
        Long sizeId,
        String name,
        String color,
        String articul,
        String brand,
        BigDecimal price,
        double rating,
        int amountOfReviews,
        int percentageOfLikes,
        List<ColorResponse> colors,
        List<SizeResponse> sizes,
        String description
) {
}


