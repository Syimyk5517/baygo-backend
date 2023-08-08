package com.example.baygo.db.dto.request.supply;

import lombok.Builder;

@Builder
public record NumberOfProductsRequest(
        int barcodeProduct,
        int quantityProduct
) {
}
