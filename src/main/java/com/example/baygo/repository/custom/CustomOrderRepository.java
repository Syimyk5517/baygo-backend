package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.AnalysisResponse;
import com.example.baygo.db.dto.response.OrderResponse;
import com.example.baygo.db.dto.response.OrderWareHouseResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.model.enums.Status;

import java.util.Date;
import java.util.List;

public interface CustomOrderRepository {
    PaginationResponse<OrderResponse> getAll(int page, int size, String keyword, Status status, Long sellerId);

    AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameofTime, Long sellerId);

    List<OrderWareHouseResponse> getAllOrders(Long sellerId);
}
