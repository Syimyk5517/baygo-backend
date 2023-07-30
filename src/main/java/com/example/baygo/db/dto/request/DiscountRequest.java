package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public record DiscountRequest(
        @NotNull(message = "Продукт должна быть указана!!!")
        List<Long> subProductsId,
        @NotBlank(message = "Название продукта должно быть указано!!!")
        String nameOfDiscount,
        @Future(message = "Дата окончания должна быть в будущем времени!!!")
        LocalDateTime dateOfFinish,
        @NotNull(message = "Процент должен быть указан!!!")
        @Positive(message = "Процент должен быть только положительным числом")
        int percent
) {
}
