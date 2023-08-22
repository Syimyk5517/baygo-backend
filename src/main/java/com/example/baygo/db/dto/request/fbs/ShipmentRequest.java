package com.example.baygo.db.dto.request.fbs;

import com.example.baygo.db.model.enums.ShippingType;
import com.example.baygo.db.model.enums.TypeOfProduct;
import com.example.baygo.db.model.enums.TypeOfSupplier;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ShipmentRequest(
        @NotNull(message = "Тип поставщика не может быть пустым")
        TypeOfSupplier typeOfSupplier,
        @NotNull(message = "Тип продукта не может быть пустым")
        TypeOfProduct typeOfProduct,
        @NotNull(message = "Способ отгрузки не может быть пустым")
        ShippingType shippingType,
        @NotNull(message = "Идентификатор склада не может быть пустым")
                Long wareHouseId){

}


