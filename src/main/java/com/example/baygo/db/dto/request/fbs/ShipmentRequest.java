package com.example.baygo.db.dto.request.fbs;

import com.example.baygo.db.model.enums.ShippingType;
import com.example.baygo.db.model.enums.TypeOfProduct;
import com.example.baygo.db.model.enums.TypeOfSupplier;
import lombok.Builder;

@Builder
public record ShipmentRequest(
        TypeOfSupplier typeOfSupplier,
        TypeOfProduct typeOfProduct,
        ShippingType shippingType,
        Long wareHouseId
) {
}
