package com.example.baygo.db.dto.response.fbs;

import com.example.baygo.db.model.enums.FBSSupplyStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetAllFbsSupplies(
        Long id,
        String name,
        LocalDate createAt,
        Long totalQuantity,
        String QRCode,
        FBSSupplyStatus status
) {
}
