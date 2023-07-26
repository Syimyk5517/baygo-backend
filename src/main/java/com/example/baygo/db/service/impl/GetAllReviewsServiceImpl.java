package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.repository.custom.CustomGetAllReviewsRepository;
import com.example.baygo.db.service.GetAllReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllReviewsServiceImpl implements GetAllReviewsService {
    private final CustomGetAllReviewsRepository customGetAllReviewsRepository;

    @Override
    public List<GetAllReviewsResponse> getAllReviews() {
        return customGetAllReviewsRepository.getAllReviews();
    }
}
