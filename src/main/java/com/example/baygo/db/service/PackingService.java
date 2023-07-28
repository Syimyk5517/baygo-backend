package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.PackingRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface PackingService {
    SimpleResponse repacking(Long supplyId, List<PackingRequest> packingRequests);
}
