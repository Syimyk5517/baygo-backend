package com.example.baygo.db.dto.request.excel;

public record ProductInfoRequest(
        String barcodeProduct,
        int quantityProduct
) {
}
