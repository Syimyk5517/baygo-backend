package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.NotNull;

public record SizeRequest(
        @NotNull(message = "Размер должен быть указан!!!")
        String size,
        @NotNull(message = "Баркод должен быть указан!!!")
        String barcode
) {
}
