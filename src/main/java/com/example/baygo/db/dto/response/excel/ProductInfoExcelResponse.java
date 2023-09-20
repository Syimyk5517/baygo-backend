package com.example.baygo.db.dto.response.excel;

import lombok.Builder;

@Builder
public record ProductInfoExcelResponse(
        String barcodeProduct,
        String quantityProduct,
        String barcodeBox
) {
}
