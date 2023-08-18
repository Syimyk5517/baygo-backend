package com.example.baygo.db.dto.request.fbs;

import lombok.Builder;

@Builder
public record SupplyOrderRequest(
        Long orderId,
        String nameOfSupply
) {
}
