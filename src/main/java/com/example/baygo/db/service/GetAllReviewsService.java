package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface GetAllReviewsService {
    List<GetAllReviewsResponse>getAllReviews();
}
