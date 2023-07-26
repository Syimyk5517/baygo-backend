package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AnswerOfSellerRequest(
        @NotNull(message = "Вопрос должен быть указан!!!")
        Long questionId,
        @NotBlank(message = "Ответ не должен быть пустым!!!")
        String answer
) {
}
