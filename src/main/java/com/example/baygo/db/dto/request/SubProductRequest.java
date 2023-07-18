package com.example.baygo.db.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record SubProductRequest(
        @NotBlank(message = "Код цвета должен быть указан!!!")
        String colorHexCode,
        @NotBlank(message = "Цвет должен быть указан!!!")
        String color,
        @NotNull(message = "Цена должна быть указана!!!")
        @Min(value = 0, message = "Цена должна быть положительным числом!!")
        BigDecimal price,
        @Size(min = 2, max = 6, message = "Размер должен быть от 2 до 6.")
        @NotEmpty(message = "Изображения должны быть указаны!!!")
        List<String> images,
        @Valid
        @NotEmpty(message = "Размеры не должны быть пустыми!!!")
        List<SizeRequest>sizes
) {
}
