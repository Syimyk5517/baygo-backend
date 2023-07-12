package com.example.baygo.service;

import com.example.baygo.dto.response.TransitDirectionResponse;

import java.util.List;

public interface SupplierService {
    List<TransitDirectionResponse> getAllTransactions();
}
