package com.example.baygo.db.dto.request.order;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductOrderRequest(
        @NotNull(message = "Идентификатор размера не может быть пустым")
        Long sizeId,
        @Min(value = 1, message = "Количество продукт должно быть больше нуля")
        int quantityProduct,
        @Min(value = 1, message = "Количество скидки быть больше нуля")
        int percentOfDiscount
) {
}
