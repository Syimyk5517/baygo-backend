package com.example.baygo.db.dto.response;


public record SupplyProductResponse(
        String photo,
        String barcode,
        int quantity,
        String productName,
        String sellerVendorCode,
        String brandName,
        int size,
        String color
) {
}