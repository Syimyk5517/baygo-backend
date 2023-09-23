package com.example.baygo.db.dto.response.fbs;

import com.example.baygo.db.model.enums.ShippingType;
import com.example.baygo.db.model.enums.TypeOfProduct;
import com.example.baygo.db.model.enums.TypeOfSupplier;

public record SellerFBSWarehousesResponse(
        Long warehouseId,
        String name,
        String address,
        boolean isActive,
        int countOfDaysToAssembly,
        ShippingType shippingType,
        TypeOfProduct typeOfProduct,
        TypeOfSupplier typeOfSupplier,
        int countOfProducts

) {
}
