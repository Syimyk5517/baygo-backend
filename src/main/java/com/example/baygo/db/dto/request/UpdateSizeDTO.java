package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateSizeDTO(
        @NotNull(message = "Размер должен быть указан!!!")
        Long sizeId,
        @NotNull(message = "Размер должен быть указан!!!")
        String size
) {
}