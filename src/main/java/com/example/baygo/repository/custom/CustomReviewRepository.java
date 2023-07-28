package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.PaginationReviewResponse;
import com.example.baygo.db.dto.response.ReviewResponse;

public interface CustomReviewRepository {
    PaginationReviewResponse<ReviewResponse> getAllReviews(Long sellerId, String keyword, int page, int size);
}
