package com.example.baygo.db.repository.custom;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;

import java.util.List;

public interface CustomQuestionRepository {
    List<BuyerQuestionResponse> getAllQuestions();
}
