package com.example.baygo.service;

import com.example.baygo.db.dto.request.PackingRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PackingService {
    SimpleResponse repacking(Long supplyId, List<PackingRequest> packingRequests);
}
