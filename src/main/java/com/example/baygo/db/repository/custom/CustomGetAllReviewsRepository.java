package com.example.baygo.db.repository.custom;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;

import java.util.List;

public interface CustomGetAllReviewsRepository {
    List<GetAllReviewsResponse> getAllReviews();
}
