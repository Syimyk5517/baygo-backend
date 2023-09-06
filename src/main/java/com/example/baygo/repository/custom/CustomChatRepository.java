package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.chat.ChatResponse;
import com.example.baygo.db.dto.response.chat.NewMessageResponse;

import java.util.List;

public interface CustomChatRepository {
    List<ChatResponse> findAll();
    ChatResponse findById(Long targetChatId);
    List<NewMessageResponse> hasNewMessage();

}
