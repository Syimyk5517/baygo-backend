package com.example.baygo.db.repository.customRepository;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SupplyProductResponse;

public interface SupplyCustomRepository {
    PaginationResponse<SupplyProductResponse> searchSupplyProducts(String keyWord, int page, int size);

}
