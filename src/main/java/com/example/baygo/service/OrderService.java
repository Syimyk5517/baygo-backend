package com.example.baygo.service;

import com.example.baygo.db.dto.response.orders.AnalysisResponse;
import com.example.baygo.db.dto.response.orders.OrderResponse;
import com.example.baygo.db.dto.response.orders.OrderWareHouseResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.orders.RecentOrdersResponse;
import com.example.baygo.db.model.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public interface OrderService {

    AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameOfTime);

    List<OrderWareHouseResponse> getAllOrdersByWareHouse();

    List<RecentOrdersResponse> getResentOrders();

    PaginationResponse<OrderResponse> getAllOrdersByFilter(int page, int size, String keyword, OrderStatus status);

}
