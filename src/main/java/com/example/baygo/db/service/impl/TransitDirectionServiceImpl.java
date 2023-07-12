package com.example.baygo.db.service.impl;

import com.example.baygo.dto.response.TransitDirectionResponse;
import com.example.baygo.db.service.SupplierService;
import com.example.baygo.repository.custom.CustomTransitDirectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitDirectionServiceImpl implements SupplierService {
    private final CustomTransitDirectionRepository customTransitDirectionRepository;
    @Override
    public List<TransitDirectionResponse> getAllTransactions(String location) {
        return customTransitDirectionRepository.getAllTransactions(location);
    }
}
