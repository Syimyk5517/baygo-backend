package com.example.baygo.db.dto.response;

public record SizeSellerResponse(
        Long sizeId,
        String size,
        int barcode,
        int quantity
) {
}
