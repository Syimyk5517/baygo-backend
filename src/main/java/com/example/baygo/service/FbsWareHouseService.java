package com.example.baygo.service;

import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.WarehouseRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface FbsWareHouseService {
    SimpleResponse saveWarehouse(WarehouseRequest wareHouseRequest);

    SimpleResponse saveShippingMethod(ShipmentRequest shipmentRequest);
}
