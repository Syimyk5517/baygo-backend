package com.example.baygo.repository.custom;

import com.example.baygo.dto.request.TransitDirectionRequest;
import com.example.baygo.dto.response.TransitDirectionResponse;

import java.util.List;

public interface CustomTransitDirectionRepository {
    List<TransitDirectionResponse> getAllTransactions(String location);
}
