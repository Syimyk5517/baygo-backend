package com.example.baygo.db.dto.response;

public record SizeSellerResponse(
        Long sizeId,
        String size,
        String barcode,
        int quantity
) {
}
