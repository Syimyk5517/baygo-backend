package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DayPromotion(
        int time,
        LocalDate dateOfFinish
) {
}
