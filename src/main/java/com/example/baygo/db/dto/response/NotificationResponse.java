package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record NotificationResponse(Long id,
                                   String tittle,
                                   String message,
                                   Boolean read,
                                   LocalDate creatAtDate,
                                   LocalTime createAtTime) {
}
