package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record BGWarehouseResponse(
        Long id,
        String region,
        String address
) {
}
