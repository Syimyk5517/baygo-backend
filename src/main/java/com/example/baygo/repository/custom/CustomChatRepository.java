package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.ChatResponse;

import java.util.List;

public interface CustomChatRepository {
    List<ChatResponse> findAll();

}
