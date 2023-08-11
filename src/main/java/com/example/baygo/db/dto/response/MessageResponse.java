package com.example.baygo.db.dto.response;


import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record MessageResponse(
        Long id,
        String image,
        String message,
        LocalDate localDate,
        String timezone,
        Boolean isSeller
) {
}
