package com.example.baygo.service;

import com.example.baygo.db.dto.request.fbs.AccessCardRequest;
import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.WareHouseRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface FbsWareHouseService {
    SimpleResponse saveWarehouse(WareHouseRequest wareHouseRequest);

    SimpleResponse saveAccess(AccessCardRequest accessCardRequest);

    SimpleResponse saveShippMethod(ShipmentRequest shipmentRequest);
}
