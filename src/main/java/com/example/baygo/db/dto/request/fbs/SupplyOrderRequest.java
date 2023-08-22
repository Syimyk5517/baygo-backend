package com.example.baygo.db.dto.request.fbs;

import com.example.baygo.db.dto.request.AccessCardRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SupplyOrderRequest(
        @NotNull(message = "Идентификатор размера заказа не может быть пустым")
        Long orderSizeId,
        @NotBlank(message = "Имя поставки не может быть пустым")
        String nameOfSupply,
        @NotNull(message = "Идентификатор склада не может быть пустым")
        Long wareHouseId,
        @Valid AccessCardRequest accessCardRequest
) {
}
