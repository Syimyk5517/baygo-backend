package com.example.baygo.service;

import com.example.baygo.db.dto.response.FBSPercentageResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.fbs.FBSOrdersResponse;

public interface FBSOrderService {
    PaginationResponse<FBSOrdersResponse> getAllFbsOrdersOnPending(int page, int size, String keyword, boolean isNews);

    FBSPercentageResponse fbsPercentage();
}
