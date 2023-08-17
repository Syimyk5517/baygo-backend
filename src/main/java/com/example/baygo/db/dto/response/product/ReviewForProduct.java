package com.example.baygo.db.dto.response.product;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReviewForProduct(
        Long id,
        String fullName,
        LocalDate createAt,
        int grade,
        String colorName,
        String photo,
        String description,
        String image
) {
}
