package com.example.baygo.db.dto.request.fbb;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record NumberOfProductsRequest(
        @Positive(message = "Баркод продукта должен быть положительным")
        String barcodeProduct,
        @Min(value = 1, message = "Количество должно быть больше нуля")
        int quantityProduct
) {
}
