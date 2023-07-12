package com.example.baygo.dto.response;

import com.example.baygo.db.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
public class TransitDirectionResponse {
    private Long warehouseId;
    private int transitWareHouse;
    private String location;
    private BigDecimal supplyCost;
}
