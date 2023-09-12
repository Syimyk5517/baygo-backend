package com.example.baygo.db.dto.response;

import lombok.*;

import java.time.LocalDate;

@Builder
public record CalendarActionResponse(

        LocalDate dateOfStart,
        String nameOfDiscount,
        int time,
        LocalDate dateOfFinish
) {


}
