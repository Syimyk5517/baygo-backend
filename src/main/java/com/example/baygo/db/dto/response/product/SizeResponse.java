package com.example.baygo.db.dto.response.product;

import lombok.Builder;

@Builder
public record SizeResponse(
        Long sizeId,
        String size,
        int quantity
) {
}
