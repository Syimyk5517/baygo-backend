package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.PaginationReviewAndQuestionResponse;
import com.example.baygo.db.dto.response.ReviewResponse;
import com.example.baygo.db.dto.response.product.ReviewGetByIdResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomReviewRepository {
    PaginationReviewAndQuestionResponse<ReviewResponse> getAllReviews(Long sellerId, String keyword, boolean isAnswered, int page, int size);

    List<GetAllReviewsResponse> getAllReviewsForSeller(Long sellerId);

    ReviewGetByIdResponse getAllReviewsOfProduct(Long subProductId, boolean withImages);
}
