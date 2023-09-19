package com.example.baygo.db.dto.response.product;

import lombok.Builder;

@Builder
public record ImagesResponse(
        Long id,
        String link
) {
}
