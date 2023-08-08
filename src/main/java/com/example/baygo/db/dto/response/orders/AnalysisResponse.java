package com.example.baygo.db.dto.response.orders;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AnalysisResponse(
        BigDecimal amountOfPrice,
        BigDecimal commission

) {
}
