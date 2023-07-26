package com.example.baygo.db.service;

import com.example.baygo.dto.response.SupplyTransitDirectionResponse;

import java.util.List;

public interface SupplyTransitDirectionService {
    List<SupplyTransitDirectionResponse> getAllTransactions(String location);
}
