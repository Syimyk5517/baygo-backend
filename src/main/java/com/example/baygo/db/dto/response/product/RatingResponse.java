package com.example.baygo.db.dto.response.product;

import lombok.Builder;

@Builder
public record RatingResponse(
        int grade,
        double percentage
) {
}
