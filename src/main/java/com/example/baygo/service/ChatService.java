package com.example.baygo.service;

import com.example.baygo.db.dto.request.MessageRequest;
import com.example.baygo.db.dto.response.ChatResponse;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface ChatService {
    SimpleResponse sendMessage(MessageRequest messageRequest);
    List<ChatResponse> findAll();
    SimpleResponse updateMessage(Long messageId,MessageRequest messageRequest);
    SimpleResponse deleteMessage(Long messageId);
    SimpleResponse deleteChat(Long chatId);
}
