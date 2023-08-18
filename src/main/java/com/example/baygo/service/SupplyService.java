package com.example.baygo.service;

import com.example.baygo.db.dto.request.fbs.SupplyOrderRequest;
import com.example.baygo.db.dto.request.fbs.SupplyRequest;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders;
import com.example.baygo.db.model.enums.SupplyStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface SupplyService {
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, int page, int pageSize);

    PaginationResponse<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page);

    SupplyResponse getSupplyById(Long id);

    PaginationResponse<SupplyProductResponse> getSupplyProducts(Long supplyId, String keyWord, int page, int size);

    List<SupplyTransitDirectionResponse> getAllTransitDirections(String transitWarehouse, String destinationWarehouse);

    List<SupplyLandingPage> getAllSupplyForLanding();

    SimpleResponse saveSupply(SupplyRequest supplyRequest);

    List<GetAllFbsSupplies> getAllFbsSupplies();


    GetSupplyWithOrders getSupplyByIdwithOrders(Long supplyId);

    SimpleResponse saveAssemblyTask(SupplyOrderRequest supplyOrderRequest);
}
