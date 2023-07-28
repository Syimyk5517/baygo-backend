package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.PaginationReviewResponse;
import com.example.baygo.db.dto.response.ReviewResponse;

import java.util.List;

public interface CustomReviewRepository {
    PaginationReviewResponse<ReviewResponse> getAllReviews(Long sellerId, String keyword, int page, int size);

    List<GetAllReviewsResponse> getAllReviewsForSeller(Long sellerId);
}
