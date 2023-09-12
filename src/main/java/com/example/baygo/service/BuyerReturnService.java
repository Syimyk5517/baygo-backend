package com.example.baygo.service;

import com.example.baygo.db.dto.request.buyer.ReturnProductRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.ReturnGetByIdResponse;
import com.example.baygo.db.dto.response.buyer.ReturnsResponse;

import java.util.List;

public interface BuyerReturnService {
    SimpleResponse save(ReturnProductRequest request);

    List<ReturnsResponse> getAll();

    ReturnGetByIdResponse getById(Long returnId);
}
