package com.example.baygo.db.dto.response.fbs;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrdersResponse(
        Long id,
        String photo,
        int barcode,
        int quantity,
        String name,
        String articulOfSeller,
        String brand,
        String size,
        String color,
        BigDecimal price,
        String address,
        OrderStatus orderStatus,
        LocalDateTime dateOfOrder

) {
}
