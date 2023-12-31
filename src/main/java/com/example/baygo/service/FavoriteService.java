package com.example.baygo.service;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.FavoriteResponse;


public interface FavoriteService {
    PaginationResponse<FavoriteResponse> getAllFavorProduct(int page, int size, String search);

    SimpleResponse toggleFavorite(Long subProductId);

    SimpleResponse deleteFavor();
}
