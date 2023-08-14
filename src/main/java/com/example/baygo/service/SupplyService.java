package com.example.baygo.service;

import com.example.baygo.db.dto.request.fbs.SupplyRequest;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.dto.response.SupplyTransitDirectionResponse;
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
}
