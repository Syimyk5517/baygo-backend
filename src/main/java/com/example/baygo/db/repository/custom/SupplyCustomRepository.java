package com.example.baygo.db.repository.custom;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.model.enums.SupplyStatus;


public interface SupplyCustomRepository {
    PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(Long currentUserId, String supplyNumber, SupplyStatus status, int page, int pageSize);

    PaginationResponse<SupplyProductResponse> searchSupplyProducts(Long id,String keyWord, int page, int size);
}
