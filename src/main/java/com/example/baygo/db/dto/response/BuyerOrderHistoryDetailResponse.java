package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record BuyerOrderHistoryDetailResponse(
        LocalDateTime dateOfOrder,
        String orderNumber,
        boolean withDelivery,
        BigDecimal beforeDiscountPrice,
        BigDecimal discountPrice,
        BigDecimal afterDiscountPrice

) {
}
