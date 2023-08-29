package com.example.baygo.db.dto.response.supply;

import lombok.Builder;

@Builder
public record WarehouseResponse(
        Long warehouseId,
        String warehouseName
) {
}
