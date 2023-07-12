package com.example.baygo.db.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductRequest (
        @NotNull(message = "Категория должна быть указана!!!")
        Long categoryId,
        @NotNull(message = "Марка должна быть указана!!!")
        Long brandId,
        @NotBlank(message = "Страна производства должна быть указана!!!")
        String manufacturer,
        @NotBlank(message = "Название продукта должно быть указано!!!")
        String name,
        @NotBlank(message = "Фасон должен быть указан!!!")
        String style,
        @NotBlank(message = "Сезон должен быть указан!!!")
        String season,
        @NotBlank(message = "Состав должен быть указан!!!")
        String composition,
        @NotBlank(message = "Характеристика должна быть указана!!!")
        String description,
        @Valid
        @NotEmpty(message = "Под продукты не должны быть пустыми!!!")
        List<SubProductRequest> subProducts
){
}
