package com.example.baygo.service;

import com.example.baygo.db.dto.request.BuyerProfileImageRequest;
import com.example.baygo.db.dto.request.BuyerProfileRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface BuyerProfileService {
    SimpleResponse updateProfile(BuyerProfileRequest request);

    SimpleResponse updateProfileImage(BuyerProfileImageRequest request);

    SimpleResponse deleteProfile();

    SimpleResponse toggleFavorite(Long subProductId);

    SimpleResponse deleteFavor();

    SimpleResponse removeProduct(Long subProductId);
}
