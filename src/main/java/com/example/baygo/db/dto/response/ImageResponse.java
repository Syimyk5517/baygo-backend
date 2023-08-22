package com.example.baygo.db.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ImageResponse(
        @NotBlank(message = "Поле 'Изображение' не должно быть пустым")
        String image) {
}
