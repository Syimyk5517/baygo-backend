package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record NotificationResponse(Long id,
                                   String tittle,
                                   String message,
                                   Boolean read,
                                   LocalDateTime creatAt) {
}
