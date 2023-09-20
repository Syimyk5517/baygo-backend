package com.example.baygo.service;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.admin.AdminFBBOrderResponse;
import com.example.baygo.db.dto.response.admin.AdminFBSOrderResponse;
import com.example.baygo.db.model.enums.OrderStatus;

import java.util.List;

public interface AdminOrderService {
    SimpleResponse statusChange(List<Long> orderIds, OrderStatus orderStatus);

    List<OrderStatus> getAllStatus();

    PaginationResponse<AdminFBBOrderResponse> getAllFBBOrders(String keyWord, int page, int size);

    long countFBBOrder();

    PaginationResponse<AdminFBSOrderResponse> getAllFBSOrders(String keyWord, int page, int size);

    long countFBSOrder();
}
