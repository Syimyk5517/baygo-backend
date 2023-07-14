package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.SellerProfileRequest;
import com.example.baygo.db.dto.request.SellerStoreInfoRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface SellerService {
    SimpleResponse sellerProfile(Long userId, SellerProfileRequest request);
    SimpleResponse sellerStoreInfo(Long sellerId, SellerStoreInfoRequest request);

}

