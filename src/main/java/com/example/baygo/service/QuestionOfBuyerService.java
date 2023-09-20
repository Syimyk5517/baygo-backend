package com.example.baygo.service;

import com.example.baygo.db.dto.request.QuestionOfBuyerRequest;
import com.example.baygo.db.dto.response.QuestionBuyerResponse;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface QuestionOfBuyerService {
    SimpleResponse saveQuestions(QuestionOfBuyerRequest request);

    List<QuestionBuyerResponse> getQuestionsOfSubProduct(Long productId);
}
