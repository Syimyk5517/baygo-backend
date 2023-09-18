package com.example.baygo.db.dto.response.orders;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record FBBOrderResponse(
        Long orderId,
        String barcode,
        String mainPhoto,
        String articulOfSeller,
        String productName,
        int quantity,
        String fullName,
        BigDecimal productPrice,
        LocalDateTime orderDate,
        OrderStatus status
) {
}
