package com.example.baygo.db.dto.response.supply;

import lombok.Builder;

@Builder
public record NumberOfProductResponse(
        String barcodeProduct,
        int quantityProduct
) {
}
