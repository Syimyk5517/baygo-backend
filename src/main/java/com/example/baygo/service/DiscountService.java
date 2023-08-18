package com.example.baygo.service;

import com.example.baygo.db.dto.request.DiscountRequest;
import com.example.baygo.db.dto.response.CalendarActionResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface DiscountService {
    SimpleResponse saveDiscount(DiscountRequest request);

    void deleteExpiredDiscount();
    List<CalendarActionResponse> getAllDiscount(LocalDate date);
}