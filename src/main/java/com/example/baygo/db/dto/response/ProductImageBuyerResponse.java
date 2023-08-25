package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record ProductImageBuyerResponse(
        Long sizeId,
        String image
) {
}
