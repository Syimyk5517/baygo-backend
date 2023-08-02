package com.example.baygo.service;

import com.example.baygo.db.dto.request.BuyerProfileImageRequest;
import com.example.baygo.db.dto.request.BuyerProfileRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface BuyerProfileService {
    SimpleResponse updateProfile(BuyerProfileRequest request);
    SimpleResponse updateProfileImage(BuyerProfileImageRequest request);
    SimpleResponse deleteProfile();
}
