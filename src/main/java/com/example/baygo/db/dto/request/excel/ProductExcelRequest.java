package com.example.baygo.db.dto.request.excel;

public record ProductExcelRequest(
        String barcodeProduct,
        String quantityProduct
) {
}
