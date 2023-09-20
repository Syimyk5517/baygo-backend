package com.example.baygo.db.dto.response.admin;

import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.model.enums.SupplyType;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record AdminSupplyGetAllResponse(
        Long supplyId,
        String numberOfSupply,
        SupplyType supplyType,
        int acceptedProduct,
        LocalDate actualDAte,
        SupplyStatus supplyStatus,
        String fullNameOfSeller
) {
}
