package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;


import java.util.List;

public interface GetAllQuestionService {
    List<BuyerQuestionResponse> getAllQuestions();
}
