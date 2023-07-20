package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.PaginationReviewResponse;
import com.example.baygo.db.dto.response.ReviewResponse;

public interface ReviewService {
    PaginationReviewResponse<ReviewResponse> getAllReviews(String keyword, int page, int size);
}
