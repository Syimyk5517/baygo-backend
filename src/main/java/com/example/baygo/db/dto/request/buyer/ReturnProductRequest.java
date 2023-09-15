package com.example.baygo.db.dto.request.buyer;

import com.example.baygo.validations.ImageUrlsValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ReturnProductRequest(
        Long orderId,
        Long orderSizeId,
        int quantityProduct,
        Boolean withDelivery,
        @NotBlank(message = "Причина должна быть указано!")
        String returnReason,
        @ImageUrlsValid(message = "Неверный формат фоты!")
        @Size(max = 8)
        List<String> returnImages,
        String country,
        String city,
        String address,
        int postalCode,
        String phoneNumber
) {
}
