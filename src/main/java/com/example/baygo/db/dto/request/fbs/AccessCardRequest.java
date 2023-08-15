package com.example.baygo.db.dto.request.fbs;

import lombok.Builder;

@Builder
public record AccessCardRequest(
        Long warehouseId,
        String driverFirstName,
        String driverLastname,
        String brand,
        String number
) {
}
