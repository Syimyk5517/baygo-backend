package com.example.baygo.db.dto.request;

import com.example.baygo.validations.NameValid;
import jakarta.validation.constraints.*;

import java.util.List;

public record ReviewByBuyerRequest(
        @NotNull(message = "Идентификатор продукта должен быть указан!!!")
        Long subProductId,
        @NotBlank(message = "Необходимо указать имя!")
        @NameValid(message = "Имя должно содержать от 2 до 33 символов!")
        String name,
        @NotBlank(message = "Почта не должна быть пустой!")
        @Email(message = "Напишите действительный адрес электронной почты!")
        String email,
        @NotBlank(message = "Вопрос не должен быть пустым!")
        String text,
        @NotNull(message = "Оценка не должна быть пустым!")
        int grade,
        @Size(min = 2, max = 6, message = "Размер должен быть от 2 до 6.")
        @NotEmpty(message = "Изображения должны быть указаны!!!")
        List<String> images
) {
}
