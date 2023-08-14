package com.example.baygo.db.dto.request.fbs;

import lombok.Builder;

@Builder
public record SupplySizeQuantityRequest(
        Long sizeId,
        int quantity
) {
}
