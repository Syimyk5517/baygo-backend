package com.example.baygo.db.dto.response.buyer;


public record ReturnsResponse(
        Long id,
        int barcode,
        String mainImage,
        String productName,
        String size,
        int quantity

) {

}
