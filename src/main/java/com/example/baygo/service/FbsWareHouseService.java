package com.example.baygo.service;

import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.WarehouseRequest;
import com.example.baygo.db.dto.response.SellerFBSWarehouseResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.FBSWareHouseAddProduct;
import com.example.baygo.db.dto.response.fbs.ProductGetAllResponse;
import com.example.baygo.db.dto.response.fbs.SellerFBSWarehousesResponse;

import java.util.List;

public interface FbsWareHouseService {
    SimpleResponse saveWarehouse(WarehouseRequest wareHouseRequest);

    SimpleResponse saveShippingMethod(ShipmentRequest shipmentRequest);

    SimpleResponse addProduct(FBSWareHouseAddProduct fbsWareHouseAddProduct);

    List<ProductGetAllResponse> getAllProduct(Long wareHouseId, String keyWord);

    List<SellerFBSWarehouseResponse> getSellerFBSWarehouses();

    List<SellerFBSWarehousesResponse> getAllSellerFBSWarehouses();

}
