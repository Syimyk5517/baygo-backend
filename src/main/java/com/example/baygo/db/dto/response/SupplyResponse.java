package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record SupplyResponse(
        Long supplyId,
        String supplyNumber,
        String warehouseName,
        String supplyType,
        String sellerPhoneNumber,
        BigDecimal supplyCost,
        BigDecimal preliminaryCostOfAcceptance,
        LocalDate dateOfCreation,
        LocalDate dateOfChange,
        LocalDate plannedDate,
        LocalDate actualDate,
        int quantityProductsPcs,
        int acceptedPieces
) {
}