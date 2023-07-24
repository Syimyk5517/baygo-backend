package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.AnalysisResponse;
import com.example.baygo.db.dto.response.OrderResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.model.enums.Status;

import java.util.Date;


public interface OrderService {
    PaginationResponse<OrderResponse> getAll(int page, int size, String keyWord, Status status);

    AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameOfTime);
}