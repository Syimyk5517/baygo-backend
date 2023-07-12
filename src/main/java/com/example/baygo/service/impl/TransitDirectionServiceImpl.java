package com.example.baygo.service.impl;

import com.example.baygo.dto.response.TransitDirectionResponse;
import com.example.baygo.repository.WarehouseRepository;
import com.example.baygo.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitDirectionServiceImpl implements SupplierService {
    private final WarehouseRepository warehouseRepository;
    @Override
    public List<TransitDirectionResponse> getAllTransactions() {
        return warehouseRepository.getAllTransitions();
    }
}
