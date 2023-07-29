package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.enums.SupplyStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SupplyLandingPage(
        Long id,
        String supplyNumber,
        LocalDate createdAt,
        String wareHouseLocation,
        int quantityOfProducts,
        SupplyStatus status
) {
}
