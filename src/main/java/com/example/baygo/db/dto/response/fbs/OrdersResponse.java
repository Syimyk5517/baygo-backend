package com.example.baygo.db.dto.response.fbs;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record OrdersResponse(
        Long id,
        String photo,
        int barcode,
        int quantity,
        String name,
        String articul,
        String brand,
        String size,
        String color,
        String address,
        LocalDate dateOfOrder

) {
}
