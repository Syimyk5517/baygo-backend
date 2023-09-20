package com.example.baygo.service;

import com.example.baygo.db.dto.request.fbs.SupplyOrderRequest;
import com.example.baygo.db.dto.request.fbs.SupplyRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders;

import java.util.List;

public interface FBSSupplyService {
    SimpleResponse saveSupply(SupplyRequest supplyRequest);

    List<GetAllFbsSupplies> getAllFbsSupplies(boolean isOnAssembly);

    GetSupplyWithOrders getSupplyByIdWithOrders(Long supplyId);

    SimpleResponse saveAssemblyTask(SupplyOrderRequest supplyOrderRequest);

    SimpleResponse createSupply(String nameOfSupply);
}
