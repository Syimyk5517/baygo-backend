package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PackingRequest(
        @NotNull(message = "Баркод должен быть указан!!!")
        String barcode,
        @NotNull(message = "Количество должен быть указан!!!")
        Integer quantity
) {
}
