package com.example.baygo.db.custom;

import com.example.baygo.db.model.enums.Status;
import com.example.baygo.db.responses.AnalysisResponse;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import com.example.baygo.db.responses.SimpleResponse;

import java.util.Date;

public interface CustomOrderRepository {
    PaginationResponse<OrderResponse> getAll(int page, int size, String keyWord, Status status,Long sellerId);
    SimpleResponse deleteById(Long orderId ,Long sellerId);

    AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameofTime,boolean commission);
}
