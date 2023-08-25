package com.example.baygo.db.dto.response.fbs;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetAllFbsSupplies(
        Long id,
        String name,
        LocalDateTime createAt,
        int totalQuantity,
        String QRCode

) {
}
