package com.example.baygo.db.dto.request.order;


public record ProductOrderRequest(
        Long sizeId,
        int quantityProduct,
        int percentOfDiscount
) {
}
