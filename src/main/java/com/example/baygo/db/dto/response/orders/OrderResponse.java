package com.example.baygo.db.dto.response.orders;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record OrderResponse(
        Long orderId,
        int barcode,
        String firstName,
        BigDecimal productPrice,
        String productName,
        LocalDateTime orderDate,
        OrderStatus status
) {
}
