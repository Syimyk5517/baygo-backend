package com.example.baygo.service;

import com.example.baygo.db.dto.response.AnalysisResponse;
import com.example.baygo.db.dto.response.OrderResponse;
import com.example.baygo.db.dto.response.OrderWareHouseResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.RecentOrdersResponse;
import com.example.baygo.db.model.enums.Status;

import java.util.Date;
import java.util.List;


public interface OrderService {
    PaginationResponse<OrderResponse> getAll(int page, int size, String keyWord, Status status);

    AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameOfTime);

    List<OrderWareHouseResponse> getAllOrdersByWareHouse();

    List<RecentOrdersResponse> getResentOrders(Long sellerId);
}
