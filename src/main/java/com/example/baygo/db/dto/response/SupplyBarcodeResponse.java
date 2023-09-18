package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.awt.image.BufferedImage;

@Builder
public record SupplyBarcodeResponse(
        String barcode,
        BufferedImage barcodeImage
) {
}
