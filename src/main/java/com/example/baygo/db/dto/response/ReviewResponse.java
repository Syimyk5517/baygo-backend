package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record ReviewResponse(
        Long id,
        String review_photo,
        String vendor_code,
        int barcode,
        String product_name,
        String product_brand,
        int grade,
        String color
) {
}
