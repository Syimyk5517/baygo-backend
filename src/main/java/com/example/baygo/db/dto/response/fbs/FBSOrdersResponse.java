package com.example.baygo.db.dto.response.fbs;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record FBSOrdersResponse(
        Long orderId,
        Long orderSizeId,
        String image,
        String barcodeOfSize,
        int quantity,
        String productName,
        String articulOfSeller,
        String size,
        String color,
        BigDecimal price,
        String warehouse,
        OrderStatus orderStatus,
        LocalDateTime dateOfOrder
) {
}
