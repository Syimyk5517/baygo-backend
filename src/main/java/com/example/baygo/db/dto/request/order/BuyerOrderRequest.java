package com.example.baygo.db.dto.request.order;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record BuyerOrderRequest(
        List<ProductOrderRequest> productOrderRequests,
        CustomerInfoRequest customerInfoRequest,
        Boolean withDelivery,
        BigDecimal totalPrise

) {
}
