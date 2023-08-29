package com.example.baygo.service;

import com.example.baygo.db.dto.request.ReviewByBuyerRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface ReviewByBuyerService {
    SimpleResponse saveReview(ReviewByBuyerRequest request);
}
