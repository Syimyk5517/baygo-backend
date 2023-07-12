package com.example.baygo.db.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record SubProductRequest(
        @NotBlank(message = "Артикул продавца должен быть указан!!!")
        String vendorNumberOfSeller,
        @NotBlank(message = "Код цвета должен быть указан!!!")
        String colorHexCode,
        @NotBlank(message = "Цвет должен быть указан!!!")
        String color,
        @NotBlank(message = "Цена должна быть указана!!!")
        BigDecimal price,
        @Size(min = 2, max = 6, message = "Размер должен быть от 2 до 6.")
        @NotEmpty(message = "Изображения должны быть указаны!!!")
        List<String> images,
        @Valid
        @NotEmpty(message = "Размеры не должны быть пустыми!!!")
        List<SizeRequest>sizes
) {
}
