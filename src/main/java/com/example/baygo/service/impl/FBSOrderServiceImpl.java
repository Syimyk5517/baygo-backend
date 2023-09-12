package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.FBSPercentageResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.fbs.FBSOrdersResponse;
import com.example.baygo.repository.OrderRepository;
import com.example.baygo.service.FBSOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FBSOrderServiceImpl implements FBSOrderService {
    private final JwtService jwtService;
    private final OrderRepository orderRepository;

    @Override
    public PaginationResponse<FBSOrdersResponse> getAllFbsOrdersOnPending(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        Page<FBSOrdersResponse> orderResponses = orderRepository.getAllOrdersFbs(sellerId, keyword, pageable);
        return new PaginationResponse<>(orderResponses.getContent(),
                orderResponses.getNumber() + 1,
                orderResponses.getTotalPages());
    }

    @Override
    public FBSPercentageResponse fbsPercentage() {
        return null;
    }
}
