package com.example.baygo.db.service;

import com.example.baygo.db.custom.CustomOrderRepository;
import com.example.baygo.db.enums.StatusOrder;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final CustomOrderRepository customOrderRepository;

    @Override
    public PaginationResponse<OrderResponse> getAll(int page, int size, String keyWord, StatusOrder status) {
        return customOrderRepository.getAll(page, size, keyWord, status);
    }
}
