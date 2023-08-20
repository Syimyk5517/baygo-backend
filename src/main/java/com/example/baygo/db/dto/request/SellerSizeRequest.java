package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record SellerSizeRequest(
        @NotNull(message = "Размер должен быть указан!!!")
        String size,
        @Positive(message = "Баркод должен быть положительным !")
        int barcode
) {
}
