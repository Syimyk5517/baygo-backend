package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.SupplyResponse;

import java.util.List;

public interface SupplyService {
    SupplyResponse getSupplyById(Long id);
    List<SupplyProductResponse> searchSupplyProducts(String keyWord,int page,int size);
}
