package com.example.baygo.db.dto.response.supply;

import com.example.baygo.db.model.enums.SupplyType;
import lombok.Builder;

@Builder
public record AccessCardResponse(
        Long supplyId,
        Long accessCardId,
        String driverBarcode,
        String driverBarcodeImage,
        String driverFirstName,
        String driverLastName,
        String carBrand,
        String carNumber,
        SupplyType supplyType,
        int numberOfSeats
) {
}
