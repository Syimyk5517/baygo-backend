package com.example.baygo.db.dto.response.deliveryFactor;

import com.example.baygo.db.model.enums.SupplyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTypeResponse {
    private SupplyType deliveryType;
    private List<WarehouseCostResponse> warehouseCostResponses;
    public void addWarehouseCost(WarehouseCostResponse warehouseCostResponse) {
        warehouseCostResponses.add(warehouseCostResponse);
    }

}
