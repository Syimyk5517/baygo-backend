package com.example.baygo.db.service.impl;

import com.example.baygo.db.config.jwt.JwtService;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.repository.custom.SupplyCustomRepository;
import com.example.baygo.db.service.SupplyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupplyServiceImpl implements SupplyService {
    private final JwtService jwtService;
    private final SupplyCustomRepository customRepository;

    @Override
    public PaginationResponse<List<SuppliesResponse>> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, int page, int pageSize) {
        Long currentUserId = jwtService.getAuthenticate().getId();
        return customRepository.getAllSuppliesOfSeller(currentUserId,supplyNumber,status,page,pageSize);
    }
}
