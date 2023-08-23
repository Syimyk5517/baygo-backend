package com.example.baygo.service;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.buyer.FavoriteResponse;

import java.time.LocalDate;


public interface FavoriteService {
    PaginationResponse<FavoriteResponse> getAllFavorProduct(int page, int size, String search, LocalDate createDate);


}
