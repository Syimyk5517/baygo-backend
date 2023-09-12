package com.example.baygo.db.dto.request.buyer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ReturnProductRequest(
        Long orderId,
        Long orderSizeId,
        int quantityProduct,
        Boolean withDelivery,
        @NotBlank(message = "Причина должна быть указано!")
        String returnReason,
        @Size(min = 2, max = 6, message = "Размер должен быть от 2 до 6.")
        @NotEmpty(message = "Изображения должны быть указаны!!!")
        List<String> returnImages,
        String country,
        String city,

        String address,
        int postalCode,
        String phoneNumber
) {
}
