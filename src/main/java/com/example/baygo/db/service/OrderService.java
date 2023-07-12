package com.example.baygo.db.service;

import com.example.baygo.db.enums.StatusOrder;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;


public interface OrderService {
    PaginationResponse<OrderResponse> getAll(int page, int size, String keyWord, StatusOrder status);
}
