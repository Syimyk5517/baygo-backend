package com.example.baygo.db.custom;

import com.example.baygo.db.model.enums.Status;
import com.example.baygo.db.responses.AnalysisResponse;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;


import java.util.Date;

public interface CustomOrderRepository {
    PaginationResponse<OrderResponse> getAll(int page, int size, String keyword, Status status,Long sellerId);
    AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameofTime,Long sellerId);
}
