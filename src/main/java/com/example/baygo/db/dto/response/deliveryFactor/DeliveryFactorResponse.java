package com.example.baygo.db.dto.response.deliveryFactor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryFactorResponse{
       private Long warehouseId;
       private String warehouseName;
       private List<SupplyTypeResponse> deliveryTypeResponses ;
}
