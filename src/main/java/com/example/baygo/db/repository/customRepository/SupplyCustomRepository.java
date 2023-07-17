package com.example.baygo.db.repository.customRepository;

import com.example.baygo.db.dto.response.SupplyProductResponse;

import java.util.List;

public interface SupplyCustomRepository {
    List<SupplyProductResponse> searchSupplyProducts(String keyWord, int page, int size);

}
