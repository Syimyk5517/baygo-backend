package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.AnswerOfSellerRequest;
import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;


public interface AnswerOfSellerService {
    SimpleResponse addAnswer(AnswerOfSellerRequest request);
    PaginationResponse<BuyerQuestionResponse> getAllQuestions(String keyWord, int page, int pageSize);
}
