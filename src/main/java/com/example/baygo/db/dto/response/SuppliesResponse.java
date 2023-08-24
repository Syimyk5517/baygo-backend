package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.model.enums.SupplyType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record SuppliesResponse(
        Long id,
        String supplyNumber,
        SupplyType supplyType,
        LocalDate createdAt,
        int quantityOfProducts,
        int acceptedProducts,
        String commission,
        BigDecimal supplyCost,
        LocalDate plannedDate,
        LocalDate actualDate,
        String user,
        SupplyStatus status
) {
}
