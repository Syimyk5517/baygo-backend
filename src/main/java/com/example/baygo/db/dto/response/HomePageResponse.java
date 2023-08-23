package com.example.baygo.db.dto.response;

import com.google.common.annotations.Beta;
import lombok.Builder;

@Builder
public record HomePageResponse(
        Long productId,
        Long subProductId,
        String name,
        String mainImage
) {
}
