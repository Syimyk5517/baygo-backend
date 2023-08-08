package com.example.baygo.db.dto.request.supply;

import lombok.Builder;

@Builder
public record SupplyChooseRequest(
        Long sizeId,
        int quantityProduct
) {
}
