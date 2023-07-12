package com.example.baygo.db.custom;

import com.example.baygo.db.enums.StatusOrder;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import org.springframework.stereotype.Repository;

public interface CustomOrderRepository {
    PaginationResponse<OrderResponse> getAll(int page, int size, String keyWord, StatusOrder status);
}
