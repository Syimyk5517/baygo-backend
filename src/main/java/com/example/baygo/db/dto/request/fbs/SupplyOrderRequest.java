package com.example.baygo.db.dto.request.fbs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record SupplyOrderRequest(
        @NotNull(message = "Идентификатор поставки не может быть пустым")
        Long fbsSupplyId,
        @NotNull(message = "Идентификатор размера заказа не может быть пустым")
        List<Long> orderSizesId
) {
}
