package com.example.baygo.db.dto.response.chat;

import lombok.Builder;

@Builder
public record NewMessageResponse(
        Boolean isNewMessage,
        Long chatId,
        String fullName,
        int countNewMessage

) {

}
