package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.enums.Status;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record OrderResponse(
        Long orderId,
        int barcode,
        String firstName,
        BigDecimal productPrice,
        String productName,
        LocalDate orderDate,
        Status status
) {
}
