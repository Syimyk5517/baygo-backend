package com.example.baygo.service;

import com.example.baygo.db.dto.response.seller.SellerReturnGetByIdResponse;
import com.example.baygo.db.dto.response.seller.SellerReturnResponse;

import java.util.List;

public interface SellerReturnService {
    List<SellerReturnResponse> getAllReturns();

    SellerReturnGetByIdResponse getById(Long returnId);
}
