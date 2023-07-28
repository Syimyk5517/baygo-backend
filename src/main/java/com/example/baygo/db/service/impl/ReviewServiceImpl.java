package com.example.baygo.db.service.impl;

import com.example.baygo.db.config.jwt.JwtService;
import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.PaginationReviewResponse;
import com.example.baygo.db.dto.response.ReviewResponse;
import com.example.baygo.db.repository.custom.CustomGetAllReviewsRepository;
import com.example.baygo.db.repository.custom.CustomReviewRepository;
import com.example.baygo.db.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final CustomReviewRepository customReviewRepository;
    private final JwtService jwtService;


    @Override
    public PaginationReviewResponse<ReviewResponse> getAllReviews(String keyword, int page, int size) {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customReviewRepository.getAllReviews(sellerId, keyword, page, size);
    }

    @Override
    public List<GetAllReviewsResponse> getAllReviewsForLandingOfSeller() {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customReviewRepository.getAllReviewsForSeller(sellerId);
    }
}
