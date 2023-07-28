package com.example.baygo.service;

import com.example.baygo.db.dto.response.SupplyTransitDirectionResponse;

import java.util.List;

public interface SupplyTransitDirectionService {
    List<SupplyTransitDirectionResponse> getAllTransactions(String location);
}
