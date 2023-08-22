package com.example.baygo.service;

import com.example.baygo.db.dto.request.fbs.SupplyAccessCardRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface WareHouseService {
    SimpleResponse saveAccess(SupplyAccessCardRequest accessCardRequest);
}
