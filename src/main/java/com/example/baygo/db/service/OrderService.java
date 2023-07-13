package com.example.baygo.db.service;

import com.example.baygo.db.model.enums.Status;
import com.example.baygo.db.responses.AnalysisResponse;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import com.example.baygo.db.responses.SimpleResponse;

import java.util.Date;


public interface OrderService {
    PaginationResponse<OrderResponse> getAll(int page, int size, String keyWord, Status status);

    SimpleResponse deleteById(Long orderId);


    AnalysisResponse getWeeklyAnalisys(Date startDate, Date endDate, Long warehouseId, String nameOfTime,boolean commission);
}
