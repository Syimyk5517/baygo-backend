package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record FBSPercentageResponse(
        int totalCountOfNewOrders,
        double percentageComparedToLateOrder,
        boolean isIncreasedOrder,

        double percentOfRatingSeller,
        double percentageComparedToLateRatingSeller,
        boolean isIncreasedRatingSeller,

        double percentOfRatingRansom,
        double percentageComparedToLateRatingRansom,
        boolean isIncreasedRatingRansom
) {
}
