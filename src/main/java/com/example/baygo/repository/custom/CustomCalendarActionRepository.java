package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.CalendarActionResponse;


import java.time.LocalDate;
import java.util.List;

public interface CustomCalendarActionRepository {
    List<CalendarActionResponse> getAllDiscount(LocalDate date);
}
