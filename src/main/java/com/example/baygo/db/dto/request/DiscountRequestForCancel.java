package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DiscountRequestForCancel(
        @NotNull(message = "Продукт должна быть указана!!!")
        List<Long> subProductsId
) {
}
