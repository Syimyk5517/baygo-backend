package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.enums.Status;
import com.example.baygo.repository.OrderRepository;
import com.example.baygo.repository.custom.CustomOrderRepository;
import com.example.baygo.service.OrderService;
import lombok.RequiredArgsConstructor;
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
    public PaginationResponse<OrderResponse> getAll(int page, int size, String keyWord, Status status) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        return customOrderRepository.getAll(page, size, keyWord, status, seller.getId());
    }

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
}
