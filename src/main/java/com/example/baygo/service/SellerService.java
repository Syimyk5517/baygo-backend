package com.example.baygo.service;

import com.example.baygo.db.dto.request.SellerProfileRequest;
import com.example.baygo.db.dto.request.SellerStoreInfoRequest;
import com.example.baygo.db.dto.response.SellerProfileResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface SellerService {
    SimpleResponse updateSellerProfile(SellerProfileRequest request);

    SimpleResponse updateSellerStoreInfo(SellerStoreInfoRequest request);

    SimpleResponse updateLogoOfStore(String newLogo);

    SellerProfileResponse getSellerProfile();
}

