package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.DiscountRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface DiscountService {
      SimpleResponse saveDiscount(DiscountRequest request);
      void deleteExpiredDiscount();
}