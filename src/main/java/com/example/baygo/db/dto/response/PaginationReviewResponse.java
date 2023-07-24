package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginationReviewResponse <T>(
        int foundReviewsHigh,
        List<T> elementsHigh,
        int foundReviewsLow,
        List<T> elementsLow,
        int totalPagesHigh,
        int totalPagesLow
) {
}