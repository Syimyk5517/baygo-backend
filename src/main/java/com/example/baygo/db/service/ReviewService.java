package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ReviewResponse;

public interface ReviewService {
    PaginationResponse<ReviewResponse> getAllReviews(String keyword,int page, int size);
}
