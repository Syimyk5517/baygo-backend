package com.example.baygo.db.repository.custom;

import com.example.baygo.db.dto.response.PaginationReviewResponse;
import com.example.baygo.db.dto.response.ReviewResponse;

public interface CustomReviewRepository {
    PaginationReviewResponse<ReviewResponse> getAllReviews(Long userId,String keyword, int page, int size);
}
