package com.example.baygo.db.dto.response.buyer;


public record ReturnsResponse(
        Long id,
        String barcode,
        String mainImage,
        String productName,
        String size,
        int quantity

) {

}
