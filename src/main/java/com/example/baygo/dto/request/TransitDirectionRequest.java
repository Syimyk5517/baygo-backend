package com.example.baygo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class TransitDirectionRequest {
    private List<Long> warehouseId;
    private int transitWareHouse ;
    private BigDecimal supplyCost;

}
