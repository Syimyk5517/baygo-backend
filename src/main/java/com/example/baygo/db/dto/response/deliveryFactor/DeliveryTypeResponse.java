package com.example.baygo.db.dto.response.deliveryFactor;

import com.example.baygo.db.model.enums.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTypeResponse {
    private DeliveryType deliveryType;
    private List<WarehouseCostResponse> warehouseCostResponses = new ArrayList<>();
    public void addWarehouseCost(WarehouseCostResponse warehouseCostResponse) {
        warehouseCostResponses.add(warehouseCostResponse);
    }

}
