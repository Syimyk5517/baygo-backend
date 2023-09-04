package com.example.baygo.db.dto.request.fbs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SupplySubProductQuantityRequest(
        @NotNull(message = "Идентификатор размера не может быть пустым")
        Long sizeId,
        @Min(value = 1, message = "Количество должно быть больше нуля")
        int quantity
) {
}
