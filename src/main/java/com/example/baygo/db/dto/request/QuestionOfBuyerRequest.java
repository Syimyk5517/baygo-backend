package com.example.baygo.db.dto.request;

import com.example.baygo.validations.NameValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionOfBuyerRequest(
        @NotNull(message = "Идентификатор продукта должен быть указан!!!")
        Long productId,
        @NotBlank(message = "Необходимо указать имя!")
        @NameValid(message = "Имя должно содержать от 2 до 33 символов!")
        String name,
        @NotBlank(message = "Почта не должна быть пустой!")
        @Email(message = "Напишите действительный адрес электронной почты!")
        String email,
        @NotBlank(message = "Вопрос не должен быть пустым!")
        String text
) {
}
