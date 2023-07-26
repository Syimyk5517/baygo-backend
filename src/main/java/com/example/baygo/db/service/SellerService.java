package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.SellerProfileRequest;
import com.example.baygo.db.dto.request.SellerStoreInfoRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface SellerService {
    SimpleResponse updateSellerProfile(SellerProfileRequest request);

    SimpleResponse updateSellerStoreInfo(SellerStoreInfoRequest request);

    SimpleResponse updateLogoOfStore(String newLogo);
}

