package com.example.baygo.db.dto.request;

import com.example.baygo.validations.ImageUrlsValid;
import jakarta.validation.constraints.*;

import java.util.List;

public record ReviewByBuyerRequest(
        @NotNull(message = "Идентификатор продукта должен быть указан!!!")
        Long subProductId,
        @NotBlank(message = "Вопрос не должен быть пустым!")
        String text,
        @NotNull(message = "Оценка не должна быть пустым!")
        int grade,
        @ImageUrlsValid(message = "Не верный формат фоты!")
        List<String> images
) {
}
