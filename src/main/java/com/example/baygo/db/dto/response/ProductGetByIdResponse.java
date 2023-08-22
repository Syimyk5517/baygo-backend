package com.example.baygo.db.dto.response;

import com.example.baygo.db.dto.request.SellerSizeRequest;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductGetByIdResponse(
        @NotNull(message = "Категория должна быть указана!!!")
        Long suProductId,
        @NotBlank(message = "Страна производства должна быть указана!!!")
        String manufacture,
        @NotBlank(message = "Бренд должен быть указан!!!")
        String brand,
        @NotBlank(message = "Название продукта должно быть указано!!!")
        String name,
        @NotBlank(message = "Сезон должен быть указан!!!")
        String season,
        @NotBlank(message = "Состав должен быть указан!!!")
        String composition,
        @NotBlank(message = "Характеристика должна быть указана!!!")
        String description,
        int ArticulBG,
        @NotBlank(message = "Артикул продавца должен быть указан!!!")
        String articulOfSeller,
        @Positive(message = "Высота должен быть  положительным !")
        int height,
        @Positive(message = "Ширина должна быть положительным! ")
        int width,
        @Positive(message = "Длина должна быть положительным !")
        int length,
        @Positive(message = "Вес должен быть положительным !")
        double weight,
        @NotNull(message = "Цена должна быть указана!!!")
        @Min(value = 0, message = "Цена должна быть положительным числом!!")
        BigDecimal price,
        List<ColorResponse> color,
        @NotEmpty(message = "Размеры не должны быть пустыми!!!")
        List<SellerSizeRequest> sizes,
        @NotEmpty(message = "Список изображений не должен быть пустым")
        List<ImageResponse> image) {
}
