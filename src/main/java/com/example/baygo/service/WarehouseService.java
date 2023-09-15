package com.example.baygo.service;

import com.example.baygo.db.dto.request.fbs.SupplyAccessCardRequest;
import com.example.baygo.db.dto.response.BGWarehouseResponse;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface WarehouseService {
    SimpleResponse saveAccess(SupplyAccessCardRequest accessCardRequest);

    List<BGWarehouseResponse> getAllBGWarehouses();
}
