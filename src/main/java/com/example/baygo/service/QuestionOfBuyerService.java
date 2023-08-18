package com.example.baygo.service;

import com.example.baygo.db.dto.request.QuestionOfBuyerRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface QuestionOfBuyerService {
    SimpleResponse saveQuestions(QuestionOfBuyerRequest request);
}
