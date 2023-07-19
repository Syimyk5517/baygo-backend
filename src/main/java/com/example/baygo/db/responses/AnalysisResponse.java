package com.example.baygo.db.responses;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AnalysisResponse(
        BigDecimal amountOfPrice,
        BigDecimal commission

) {
}
