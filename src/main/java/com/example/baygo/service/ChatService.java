package com.example.baygo.service;

import com.example.baygo.db.dto.request.MessageRequest;
import com.example.baygo.db.dto.response.chat.ChatResponse;
import com.example.baygo.db.dto.response.chat.NewMessageResponse;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface ChatService {
    List<ChatResponse> findAll();
    ChatResponse findById(Long targetChatId);
    SimpleResponse sendMessage(MessageRequest messageRequest);
    List<NewMessageResponse> hasNewMessage();
    SimpleResponse updateMessage(Long messageId,MessageRequest messageRequest);
    SimpleResponse deleteMessage(Long messageId);
    SimpleResponse deleteChat(Long chatId);
}
