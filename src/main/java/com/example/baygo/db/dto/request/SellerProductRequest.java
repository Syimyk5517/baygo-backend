package com.example.baygo.db.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SellerProductRequest(
        @NotNull(message = "Категория должна быть указана!!!")
        Long subCategoryId,
        @NotBlank(message = "Страна производства должна быть указана!!!")
        String manufacturer,
        @NotBlank(message = "Бренд должен быть указан!!!")
        String brand,
        @NotBlank(message = "Название продукта должно быть указано!!!")
        String name,
        @NotBlank(message = "Сезон должен быть указан!!!")
        String season,
        @NotBlank(message = "Состав должен быть указан!!!")
        String composition,
        @Valid
        @NotEmpty(message = "Под продукты не должны быть пустыми!!!")
        List<SellerSubProductRequest> subProducts
){
}
