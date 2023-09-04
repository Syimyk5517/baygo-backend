package com.example.baygo.db.dto.response.orders;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderResponse(
        Long orderId,
        int barcode,
        String mainPhoto,
        String articulOfSeller,
        String productName,
        int quantity,
        String firstName,
        BigDecimal productPrice,
        LocalDateTime orderDate,
        OrderStatus status
) {
}
