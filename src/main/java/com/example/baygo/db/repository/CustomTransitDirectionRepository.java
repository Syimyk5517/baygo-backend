package com.example.baygo.db.repository;


import com.example.baygo.dto.response.SupplyTransitDirectionResponse;

import java.util.List;

public interface CustomTransitDirectionRepository {
    List<SupplyTransitDirectionResponse> getAllTransactions(String location);
}
