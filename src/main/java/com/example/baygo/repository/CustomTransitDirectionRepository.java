package com.example.baygo.repository;


import com.example.baygo.db.dto.response.SupplyTransitDirectionResponse;

import java.util.List;

public interface CustomTransitDirectionRepository {
    List<SupplyTransitDirectionResponse> getAllTransactions(String location);
}
