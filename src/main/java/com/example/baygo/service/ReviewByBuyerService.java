package com.example.baygo.service;

import com.example.baygo.db.dto.request.ReviewByBuyerRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.product.ReviewGetByIdResponse;

public interface ReviewByBuyerService {
    SimpleResponse saveReview(ReviewByBuyerRequest request);

    ReviewGetByIdResponse getAllReviewByProduct(Long subProductId, boolean withImages);
}
