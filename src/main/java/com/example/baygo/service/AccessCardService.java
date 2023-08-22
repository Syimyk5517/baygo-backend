package com.example.baygo.service;

import com.example.baygo.db.dto.request.AccessCardRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface AccessCardService {
    SimpleResponse save(AccessCardRequest accessCardRequest, Long supplyId);
}
