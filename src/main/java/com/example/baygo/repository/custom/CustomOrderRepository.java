package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.orders.AnalysisResponse;
import com.example.baygo.db.dto.response.orders.OrderWareHouseResponse;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomOrderRepository {

    AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameofTime, Long sellerId);

    List<OrderWareHouseResponse> getAllOrders(Long sellerId);
}
