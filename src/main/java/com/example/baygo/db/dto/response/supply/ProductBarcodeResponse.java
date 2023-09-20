package com.example.baygo.db.dto.response.supply;

import lombok.Builder;

@Builder
public record ProductBarcodeResponse(
        String barcodeProduct,
        String productName,
        String productDescription
) {
}
