package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.SupplyResponse;

import java.time.LocalDate;

public interface SupplyService {
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, int page, int pageSize);
    PaginationResponse<DeliveryFactorResponse>  findAllDeliveryFactor(String keyword, LocalDate date, int size, int page);

    SupplyResponse getSupplyById(Long id);

    PaginationResponse<SupplyProductResponse> searchSupplyProducts(Long id,String keyWord, int page, int size);

}
