package com.example.baygo.db.service.impl;

import com.example.baygo.dto.response.SupplyTransitDirectionResponse;
import com.example.baygo.db.service.SupplyTransitDirectionService;
import com.example.baygo.db.repository.CustomTransitDirectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyTransitDirectionServiceImpl implements SupplyTransitDirectionService {
    private final CustomTransitDirectionRepository customTransitDirectionRepository;
    @Override
    public List<SupplyTransitDirectionResponse> getAllTransactions(String location) {
        return customTransitDirectionRepository.getAllTransactions(location);
    }
}
