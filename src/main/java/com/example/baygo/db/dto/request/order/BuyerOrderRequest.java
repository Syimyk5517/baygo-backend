package com.example.baygo.db.dto.request.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record BuyerOrderRequest(
        @Valid
        List<ProductOrderRequest> productOrderRequests,
        @Valid
        CustomerInfoRequest customerInfoRequest,
        Boolean withDelivery,
        @DecimalMin(value = "0.01", message = "Общая стоимость продукты должна быть больше 0")
        BigDecimal totalPrice

) {
}
