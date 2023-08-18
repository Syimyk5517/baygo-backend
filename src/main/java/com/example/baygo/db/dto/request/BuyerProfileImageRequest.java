package com.example.baygo.db.dto.request;

import com.example.baygo.validations.ImageUrlValid;
import jakarta.validation.constraints.NotNull;

public record BuyerProfileImageRequest(
        @NotNull(message = "Изображение профиля не может быть пустым")
        @ImageUrlValid
        String imgUrl
) {
}
