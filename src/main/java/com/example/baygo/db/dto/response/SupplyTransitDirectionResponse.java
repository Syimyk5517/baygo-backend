package com.example.baygo.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
public class SupplyTransitDirectionResponse {
    private Long warehouseId;
    private String transitWareHouse;
    private String destinationWarehouse;
    private BigDecimal transitCost;
}
