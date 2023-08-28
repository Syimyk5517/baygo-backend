package com.example.baygo.db.dto.response.supply;

import lombok.Builder;

@Builder

public record SupplySellerProductResponse(
        Long productSizeId,
        String imageProduct,
        String categoryProduct,
        int barcodeProduct,
        String vendorCodeSeller,
        String brandProduct,
        String sizeProduct,
        String colorProduct

) {
}