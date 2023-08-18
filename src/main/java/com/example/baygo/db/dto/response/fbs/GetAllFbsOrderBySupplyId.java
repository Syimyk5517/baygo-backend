package com.example.baygo.db.dto.response.fbs;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetAllFbsOrderBySupplyId(
        String photo,
        int barcode,
        int quantity,
        String name,
        String size,
        String color,
        LocalDate dateOfOrder

) {
}
