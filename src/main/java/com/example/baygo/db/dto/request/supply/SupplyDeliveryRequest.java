package com.example.baygo.db.dto.request.supply;

import com.example.baygo.db.model.enums.SupplyType;
import lombok.Builder;

@Builder
public record SupplyDeliveryRequest(
        int deliveryPass,
        String driverName,
        String driverSurname,
        String carBrand,
        String carNumber,
        SupplyType supplyType,
        int numberOfSeats
) {
}
