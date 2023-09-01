package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.admin.AdminFBBOrderResponse;
import com.example.baygo.db.dto.response.admin.AdminFBSOrderResponse;
import com.example.baygo.db.model.OrderSize;
import com.example.baygo.db.model.enums.OrderStatus;
import com.example.baygo.repository.OrderSizeRepository;
import com.example.baygo.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {
    private final OrderSizeRepository orderRepository;

    @Override
    public void statusChange(List<Long> orderIds, OrderStatus orderStatus) {

        LocalDateTime receivedDate = LocalDateTime.now();

        List<OrderSize> ordersToUpdate = orderRepository.findAllById(orderIds);
        ordersToUpdate.forEach(order -> {
            order.setOrderStatus(orderStatus);
            order.setDateOfReceived(receivedDate);
        });

        orderRepository.saveAll(ordersToUpdate);
    }

    @Override
    public List<OrderStatus> getAllStatus() {
        return List.of(OrderStatus.values());
    }

    @Override
    public PaginationResponse<AdminFBBOrderResponse> getAllFBBOrders(String keyWord, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AdminFBBOrderResponse> allFBBOrder = orderRepository.getAllFBBOrder(keyWord, pageable);
        return new PaginationResponse<>(allFBBOrder.getContent(),
                allFBBOrder.getNumber() + 1,
                allFBBOrder.getTotalPages());
    }

    @Override
    public long countFBBOrder() {
        return orderRepository.countFBBOrders();
    }

    @Override
    public PaginationResponse<AdminFBSOrderResponse> getAllFBSOrders(String keyWord, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AdminFBSOrderResponse> allFBSOrders = orderRepository.getAllFBSOrders(keyWord, pageable);
        return new PaginationResponse<>(allFBSOrders.getContent(),
                allFBSOrders.getNumber() + 1,
                allFBSOrders.getTotalPages());
    }

    @Override
    public long countFBSOrder() {
        return orderRepository.countFBSOrders();
    }
}







