package com.example.baygo.db.dto.request;

public record MessageRequest(
        Long chatId,
        String message,
        String image
) {
}
