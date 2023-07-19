package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.SupplyResponse;


public interface SupplyService {
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, int page, int pageSize);

    SupplyResponse getSupplyById(Long id);

    PaginationResponse<SupplyProductResponse> searchSupplyProducts(String keyWord, int page, int size);

}
