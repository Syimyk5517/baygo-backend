package com.example.baygo.db.dto.response.admin;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record AdminFBBOrderResponse(
        Long orderId,
        String barcode,
        String productName,
        int quantity,
        String fullName,
        LocalDateTime orderDate,
        LocalDateTime receivedDate,
        OrderStatus status,
        String customerFullName,
        String customerAddress,
        String customerNumber
) {
}
