package com.example.baygo.db.dto.response.admin;

import com.example.baygo.db.model.enums.OrderStatus;

import java.time.LocalDateTime;

public record AdminFBSOrderResponse(
        Long id,
        String barcode,
        int quantity,
        String name,
        OrderStatus orderStatus,
        LocalDateTime dateOfOrder,
        LocalDateTime receivedAt,
        String customerFullName,
        String customerAddress,
        String customerNumber

) {
}
