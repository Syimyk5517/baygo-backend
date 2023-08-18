package com.example.baygo.service;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.PaginationReviewAndQuestionResponse;
import com.example.baygo.db.dto.response.ReviewResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    PaginationReviewAndQuestionResponse<ReviewResponse> getAllReviews(String keyword, boolean isAnswered, int page, int size);

    List<GetAllReviewsResponse> getAllReviewsForLandingOfSeller();
}
