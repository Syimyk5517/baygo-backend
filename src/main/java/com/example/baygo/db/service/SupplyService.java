package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.SupplyResponse;

public interface SupplyService {
    SupplyResponse getSupplyById(Long id);

    PaginationResponse<SupplyProductResponse> searchSupplyProducts(String keyWord, int page, int size);
}
