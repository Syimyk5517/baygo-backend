package com.example.baygo.db.dto.response.deliveryFactor;

import com.example.baygo.db.model.enums.SupplyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyTypeResponse {
    private String supplyType;
    private List<WarehouseCostResponse> warehouseCostResponses;
    public void addWarehouseCost(WarehouseCostResponse warehouseCostResponse) {
        if (warehouseCostResponses == null) {
            warehouseCostResponses = new ArrayList<>();
        }
        warehouseCostResponses.add(warehouseCostResponse);
    }

}
