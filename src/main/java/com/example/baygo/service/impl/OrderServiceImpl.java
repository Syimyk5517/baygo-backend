package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.orders.AnalysisResponse;
import com.example.baygo.db.dto.response.orders.OrderResponse;
import com.example.baygo.db.dto.response.orders.OrderWareHouseResponse;
import com.example.baygo.db.dto.response.orders.RecentOrdersResponse;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.enums.OrderStatus;
import com.example.baygo.repository.OrderRepository;
import com.example.baygo.repository.custom.CustomOrderRepository;
import com.example.baygo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final CustomOrderRepository customOrderRepository;
    private final JwtService jwtService;
    private final OrderRepository orderRepository;

    @Override
    public AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameOfTime) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        return customOrderRepository.getWeeklyAnalysis(startDate, endDate, warehouseId, nameOfTime, seller.getId());
    }

    @Override
    public List<OrderWareHouseResponse> getAllOrdersByWareHouse() {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customOrderRepository.getAllOrders(sellerId);
    }

    @Override
    public List<RecentOrdersResponse> getResentOrders() {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return orderRepository.getAllRecentOrders(sellerId);
    }

    @Override
    public PaginationResponse<OrderResponse> getAllOrdersByFilter(int page, int size, String keyword, OrderStatus status) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "dateOfOrder"));
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        Page<OrderResponse> orderResponses = orderRepository.getAllOrders(sellerId, keyword, status, pageable);
        return new PaginationResponse<>(orderResponses.getContent(),
                orderResponses.getNumber() + 1,
                orderResponses.getTotalPages());
    }
}

