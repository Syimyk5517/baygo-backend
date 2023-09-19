package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record SaveSizeRequest(
        @NotNull(message = "Размер должен быть указан!!!")
        String size,
        @Positive(message = "Баркод должен быть положительным !")
        String barcode
) {
}
