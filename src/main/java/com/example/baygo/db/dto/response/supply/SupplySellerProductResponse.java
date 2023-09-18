package com.example.baygo.db.dto.response.supply;

import lombok.Builder;

@Builder

public record SupplySellerProductResponse(
        Long productSizeId,
        String imageProduct,
        String categoryProduct,
        String barcodeProduct,
        String acticulOfSeller,
        int acticulBG,
        String brandProduct,
        String sizeProduct,
        String colorProduct
) {
}
