package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionOfBuyerRequest(
        @NotNull(message = "Идентификатор продукта должен быть указан!!!")
        Long subProductId,
        @NotBlank(message = "Вопрос не должен быть пустым!")
        String text
) {
}
