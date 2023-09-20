package com.example.baygo.service;

import com.example.baygo.db.dto.request.seller.AdvertisementSaveRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface AdvertisementService {
    SimpleResponse saveAdvertisement(AdvertisementSaveRequest saveRequest);
}
