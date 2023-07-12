package com.example.baygo.db.responses;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record OrderResponse(
        Long orderId,
        String barcode,
        String firstName,
        String productPrice,
        String productName,
        LocalDate orderDate,
        String status
) {
}
