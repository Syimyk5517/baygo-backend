package com.example.baygo.service;

import com.example.baygo.db.dto.request.DiscountRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface DiscountService {
    SimpleResponse saveDiscount(DiscountRequest request);

    void deleteExpiredDiscount();
}