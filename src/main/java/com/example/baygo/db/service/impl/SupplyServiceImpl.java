package com.example.baygo.db.service.impl;

import com.example.baygo.db.custom.CustomDeliveryFactor;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.service.SupplyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SupplyServiceImpl implements SupplyService {
    private final CustomDeliveryFactor customDeliveryFactor;
    @Override
    public List<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page) {
        return customDeliveryFactor.findAllDeliveryFactor(keyword,date,size,page);
    }
}
