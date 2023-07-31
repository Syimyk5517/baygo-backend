package com.example.baygo.service;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.PaginationReviewResponse;
import com.example.baygo.db.dto.response.ReviewResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    PaginationReviewResponse<ReviewResponse> getAllReviews(String keyword, int page, int size);

    List<GetAllReviewsResponse> getAllReviewsForLandingOfSeller();
}
