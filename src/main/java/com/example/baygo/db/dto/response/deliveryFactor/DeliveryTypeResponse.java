package com.example.baygo.db.dto.response.deliveryFactor;

import com.example.baygo.db.model.enums.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTypeResponse{
        DeliveryType deliveryType;
        List<WarehouseCostResponse> warehouseCostResponses;

}
